package com.erik.capstone.core.domain.model

data class MovieDetail(
        val id: Int,
        val title: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val backdropPath: String,
        val releaseDate: String,
        val voteAverage: Double,
        val voteCount: Int,
        val tagline: String,
        val homepage: String,
        val runtime: Int
)