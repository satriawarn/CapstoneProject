package com.erik.capstone.core.utils

import com.erik.capstone.core.data.source.local.entity.MovieDetailEntity
import com.erik.capstone.core.data.source.local.entity.MovieEntity
import com.erik.capstone.core.data.source.remote.response.MovieDetailResponse
import com.erik.capstone.core.data.source.remote.response.MovieResponse
import com.erik.capstone.core.domain.model.Movie
import com.erik.capstone.core.domain.model.MovieDetail

object DataMapper {
    fun mapMovieResponseToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val moviesList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                movieId = it.id,
                description = it.overview,
                poster_path = it.posterPath,
                movieTitle = it.title,
                popularity = it.popularity
            )
            moviesList.add(movie)
        }
        return moviesList
    }

    fun mapResponseToDomain(input: List<MovieResponse>): List<Movie> {
        return input.map {
            Movie(
                movieId = it.id,
                movieTitle = it.title,
                poster_path = it.posterPath,
                description = it.overview,
                popularity = it.popularity
            )
        }
    }


    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.movieId,
                movieTitle = it.movieTitle,
                poster_path = it.poster_path,
                description = it.description,
                popularity = it.popularity
            )
        }


    fun mapMovieDetailEntitiesToDomain(input: List<MovieDetailEntity>): List<MovieDetail> {
        return input.map {
            MovieDetail(
                id = it.id,
                title = it.title,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                tagline = it.tagline,
                homepage = it.homepage,
                runtime = it.runtime
            )
        }
    }

    fun mapDetailResponseToDomain(input: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            id = input.id,
            title = input.title,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            tagline = input.tagline,
            homepage = input.homepage,
            runtime = input.runtime
        )
    }

    fun mapDomainToEntity(input: MovieDetail): MovieDetailEntity {
        return MovieDetailEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            tagline = input.tagline,
            homepage = input.homepage,
            runtime = input.runtime
        )
    }
}