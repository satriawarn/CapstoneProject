package com.erik.capstone.core.domain.usecase

import com.erik.capstone.core.data.MovieRepository
import com.erik.capstone.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: MovieRepository) :
    MovieUseCase {

    override fun getAllMovie() = movieRepository.getAllMovie()

    override fun getFavoriteMovie(): Flow<List<MovieDetail>> = movieRepository.getFavoriteMovie()

    override suspend fun searchMovie(search: String) = movieRepository.searchMovie(search)

    override suspend fun getMovieDetail(id: Int) = movieRepository.getMovieDetail(id)

    override fun checkFavorite(id: Int): Flow<Int> = movieRepository.checkFavorite(id)

    override suspend fun insertMovieDetail(movieDetail: MovieDetail) {
        movieRepository.insertMovieDetail(movieDetail)
    }

    override suspend fun deleteFavoriteMovie(movieDetail: MovieDetail) {
        movieRepository.deleteFavoriteMovie(movieDetail)
    }
}