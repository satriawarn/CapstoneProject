package com.erik.capstone.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erik.capstone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val useCase: MovieUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(LibraryViewModel::class.java) -> {
                LibraryViewModel(useCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}