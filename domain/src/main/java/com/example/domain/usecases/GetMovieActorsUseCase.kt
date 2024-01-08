package com.example.domain.usecases

import com.example.data.network.ApiException
import com.example.data.repository.MoviesRepository
import com.example.data.service.DispatchersProvider
import com.example.domain.model.Actors
import com.example.domain.model.Resource
import com.example.domain.model.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetMovieActorsUseCase {
    fun getMovieActorsById(movieId: Int): Flow<Resource<Actors>>
}

internal class GetMovieActorsUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository, private val dispatchersProvider: DispatchersProvider
) : GetMovieActorsUseCase {
    override fun getMovieActorsById(movieId: Int): Flow<Resource<Actors>> = flow {
        try {
            emit(Resource.Loading())
            val videos = repository.getMovieActors(movieId).mapToModel()
            emit(
                Resource.Success(videos)
            )
        } catch (exception: ApiException) {
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
    }.flowOn(dispatchersProvider.io())
}
