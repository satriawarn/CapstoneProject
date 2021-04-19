package com.erik.capstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @field:SerializedName("results")
    var results: List<MovieResponse>
)