package com.erik.capstone.core.data.source.local.room

import androidx.room.*
import com.erik.capstone.core.data.source.local.entity.MovieDetailEntity
import com.erik.capstone.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_detail")
    fun getFavoriteMovie(): Flow<List<MovieDetailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Query("SELECT EXISTS (SELECT 1 FROM movie_detail WHERE movieId=:id)")
    fun checkFavorite(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetailEntity: MovieDetailEntity)

    @Delete
    suspend fun deleteFavoriteMovie(movie: MovieDetailEntity)
}