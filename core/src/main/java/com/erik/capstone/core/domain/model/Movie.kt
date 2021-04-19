package com.erik.capstone.core.domain.model

data class Movie(
    val movieId: Int,
    val movieTitle: String,
    val description: String,
    val poster_path: String? = null,
    val popularity: Double
)