package com.erik.capstone.core.data.source.remote.network

import com.erik.capstone.core.BuildConfig
import com.erik.capstone.core.data.source.remote.response.MovieDetailResponse
import com.erik.capstone.core.data.source.remote.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieListResponse

    @GET("search/movie")
    suspend fun getMovieBySearch(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apikey: String = BuildConfig.API_KEY
    ): MovieDetailResponse?
}