package com.erik.capstone.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    val id: Int,
    @ColumnInfo(name = "movieTitle")
    val title: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "popularity")
    val popularity: Double,
    @ColumnInfo(name = "posterPath")
    val posterPath: String,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,
    @ColumnInfo(name = "voteCount")
    val voteCount: Int,
    @ColumnInfo(name = "tagline")
    val tagline: String,
    @ColumnInfo(name = "homepage")
    val homepage: String,
    @ColumnInfo(name = "runtime")
    val runtime: Int
)