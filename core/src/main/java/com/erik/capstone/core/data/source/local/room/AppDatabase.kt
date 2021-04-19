package com.erik.capstone.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erik.capstone.core.data.source.local.entity.MovieDetailEntity
import com.erik.capstone.core.data.source.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}