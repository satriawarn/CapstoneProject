package com.erik.capstone.core.domain.repository

import com.erik.capstone.core.data.Resource
import com.erik.capstone.core.domain.model.Movie
import com.erik.capstone.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<MovieDetail>>

    suspend fun searchMovie(query: String): Resource<List<Movie>>

    suspend fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>

    fun checkFavorite(id: Int): Flow<Int>

    suspend fun insertMovieDetail(movieDetail: MovieDetail)

    suspend fun deleteFavoriteMovie(movieDetail: MovieDetail)
}