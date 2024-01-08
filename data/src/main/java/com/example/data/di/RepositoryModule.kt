package com.example.data.di

import com.example.data.repository.MoviesRepository
import com.example.data.repository.MoviesRepositoryImpl
import com.example.data.service.DispatchersProvider
import com.example.data.service.RuntimeDispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindsMovieRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    internal abstract fun bindsDispatcherProvider(
        runtimeDispatchersProvider: RuntimeDispatchersProvider
    ): DispatchersProvider

}