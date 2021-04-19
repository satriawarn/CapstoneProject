package com.erik.capstone.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.erik.capstone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(useCase: MovieUseCase) : ViewModel() {

    val data = useCase.getFavoriteMovie().asLiveData(viewModelScope.coroutineContext)

}