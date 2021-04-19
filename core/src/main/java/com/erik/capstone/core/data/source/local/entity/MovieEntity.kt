package com.erik.capstone.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: Int,

    @ColumnInfo(name = "movieTitle")
    var movieTitle: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "poster_path")
    var poster_path: String? = null,

    @ColumnInfo(name = "popularity")
    var popularity: Double,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)