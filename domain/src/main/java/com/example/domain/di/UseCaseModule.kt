package com.example.domain.di

import com.example.domain.usecases.GetMovieActorsUseCase
import com.example.domain.usecases.GetMovieActorsUseCaseImpl
import com.example.domain.usecases.GetMovieVideoUseCase
import com.example.domain.usecases.GetMovieVideoUseCaseImpl
import com.example.domain.usecases.GetMoviesBasedOnFiltersUseCase
import com.example.domain.usecases.GetMoviesBasedOnFiltersUseCaseImpl
import com.example.domain.usecases.GetMoviesBasedOnKeywordUseCase
import com.example.domain.usecases.GetMoviesBasedOnKeywordUseCaseImpl
import com.example.domain.usecases.GetPopularMoviesUseCase
import com.example.domain.usecases.GetPopularMoviesUseCaseImpl
import com.example.domain.usecases.GetTopRatedMoviesUseCase
import com.example.domain.usecases.GetTopRatedMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    internal abstract fun bindsGetPopularMoviesUseCase(
        getPopularMoviesUseCase: GetPopularMoviesUseCaseImpl
    ): GetPopularMoviesUseCase

    @Binds
    internal abstract fun bindGetTopRatedMoviesUseCase(
        getTopRatedMoviesUseCase: GetTopRatedMoviesUseCaseImpl
    ): GetTopRatedMoviesUseCase

    @Binds
    internal abstract fun bindsGetMoviesBasedOnFiltersUseCase(
        getMoviesBasedOnFiltersUseCaseImpl: GetMoviesBasedOnFiltersUseCaseImpl
    ): GetMoviesBasedOnFiltersUseCase

    @Binds
    internal abstract fun bindsGetMoviesBasedOnKeywordUseCase(
        getMoviesBasedOnKeywordUseCase: GetMoviesBasedOnKeywordUseCaseImpl
    ): GetMoviesBasedOnKeywordUseCase

    @Binds
    internal abstract fun bindsGetMovieVideoUseCase(
        getMovieVideoUseCaseImpl: GetMovieVideoUseCaseImpl
    ): GetMovieVideoUseCase

    @Binds
    internal abstract fun bindsGetMovieActorsUseCase(
        getMovieActorsUseCaseImpl: GetMovieActorsUseCaseImpl
    ): GetMovieActorsUseCase
}