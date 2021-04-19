package com.erik.capstone.core.data.source.remote

import android.util.Log
import com.erik.capstone.core.data.source.remote.network.ApiResponse
import com.erik.capstone.core.data.source.remote.network.ApiService
import com.erik.capstone.core.data.source.remote.response.MovieDetailResponse
import com.erik.capstone.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllMovie(): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getDiscoverMovie()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun searchMovie(query: String): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getMovieBySearch(query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<MovieDetailResponse>> =
        flow {
            try {
                val response = apiService.getMovieDetail(id)
                if (response != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}