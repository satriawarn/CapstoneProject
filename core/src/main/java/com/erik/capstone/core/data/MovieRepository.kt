package com.erik.capstone.core.data

import com.erik.capstone.core.data.source.local.LocalDataSource
import com.erik.capstone.core.data.source.remote.RemoteDataSource
import com.erik.capstone.core.data.source.remote.network.ApiResponse
import com.erik.capstone.core.data.source.remote.response.MovieResponse
import com.erik.capstone.core.domain.model.Movie
import com.erik.capstone.core.domain.model.MovieDetail
import com.erik.capstone.core.domain.repository.IMovieRepository
import com.erik.capstone.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                localDataSource.insertMovies(
                    DataMapper.mapMovieResponseToEntities(data)
                )
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<MovieDetail>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapMovieDetailEntitiesToDomain(it)
        }
    }

    override suspend fun searchMovie(search: String): Resource<List<Movie>> {
        return when (val apiResponse = remoteDataSource.searchMovie(search).first()) {
            is ApiResponse.Success -> {
                val result = DataMapper.mapResponseToDomain(apiResponse.data)
                Resource.Success(result)
            }
            is ApiResponse.Empty -> {
                Resource.Error(apiResponse.toString())
            }
            is ApiResponse.Error -> {
                Resource.Error(apiResponse.errorMessage)
            }
        }
    }

    override suspend fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>> {
        return remoteDataSource.getMovieDetail(id).map {
            when (it) {
                is ApiResponse.Success -> Resource.Success(
                    DataMapper.mapDetailResponseToDomain(it.data)
                )
                is ApiResponse.Empty -> Resource.Error(
                    it.toString()
                )
                is ApiResponse.Error -> Resource.Error(
                    it.errorMessage
                )
            }
        }
    }

    override fun checkFavorite(id: Int): Flow<Int> {
        return localDataSource.checkFavorite(id)
    }

    override suspend fun insertMovieDetail(movieDetail: MovieDetail) {
        localDataSource.insertMovieDetail(
            DataMapper.mapDomainToEntity(movieDetail)
        )
    }

    override suspend fun deleteFavoriteMovie(movieDetail: MovieDetail) {
        localDataSource.deleteFavoriteMovie(
            DataMapper.mapDomainToEntity(movieDetail)
        )
    }
}