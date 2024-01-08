package com.example.domain.usecases

import com.example.data.network.ApiException
import com.example.data.repository.MoviesRepository
import com.example.data.service.DispatchersProvider
import com.example.domain.model.ListMovies
import com.example.domain.model.Resource
import com.example.domain.model.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetTopRatedMoviesUseCase {
    fun getTopRatedMovies(currentPage: Int): Flow<Resource<ListMovies>>
}

internal class GetTopRatedMoviesUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository, private val dispatchersProvider: DispatchersProvider
) : GetTopRatedMoviesUseCase {
    override fun getTopRatedMovies(currentPage: Int): Flow<Resource<ListMovies>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getTopRatedMovies(currentPage).mapToModel()
            emit(
                Resource.Success(movies)
            )
        } catch (exception: ApiException) {
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
    }.flowOn(dispatchersProvider.io())
}
