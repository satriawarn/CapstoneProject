package com.erik.capstone.dicoding.ui.main.movies.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.erik.capstone.core.data.Resource
import com.erik.capstone.core.domain.model.MovieDetail
import com.erik.capstone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    lateinit var detail: LiveData<Resource<MovieDetail>>

    fun getDetail(id: Int) = viewModelScope.launch {
        if (!::detail.isInitialized) {
            detail = movieUseCase.getMovieDetail(id)
                .onStart {
                    emit(Resource.Loading())
                    Log.d("VM", "getDetail: $id")
                }
                .catch { exception -> Resource.Error(exception.toString(), null) }
                .asLiveData()
        }
    }

    fun checkFavorite(id: Int) = movieUseCase.checkFavorite(id).asLiveData()

    fun insertMovieDetail(movieDetail: MovieDetail) = viewModelScope.launch {
        movieUseCase.insertMovieDetail(movieDetail)
    }

    fun deleteMovieDetail(movieDetail: MovieDetail) = viewModelScope.launch {
        movieUseCase.deleteFavoriteMovie(movieDetail)
    }
}