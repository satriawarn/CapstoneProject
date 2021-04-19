package com.erik.capstone.dicoding.di

import com.erik.capstone.core.domain.usecase.MovieInteractor
import com.erik.capstone.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}