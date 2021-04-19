package com.erik.capstone.core.data.source.local

import com.erik.capstone.core.data.source.local.entity.MovieDetailEntity
import com.erik.capstone.core.data.source.local.entity.MovieEntity
import com.erik.capstone.core.data.source.local.room.AppDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val appDao: AppDao) {

    fun getAllMovie(): Flow<List<MovieEntity>> = appDao.getAllMovie()

    fun getFavoriteMovie(): Flow<List<MovieDetailEntity>> = appDao.getFavoriteMovie()

    suspend fun insertMovies(movieList: List<MovieEntity>) = appDao.insertMovie(movieList)

    suspend fun insertMovieDetail(movieDetailEntity: MovieDetailEntity) =
        appDao.insertMovieDetail(movieDetailEntity)

    fun checkFavorite(id: Int): Flow<Int> = appDao.checkFavorite(id)

    suspend fun deleteFavoriteMovie(movie: MovieDetailEntity) = appDao.deleteFavoriteMovie(movie)

}