package com.example.domain.usecases

import com.example.data.network.ApiException
import com.example.data.repository.MoviesRepository
import com.example.data.service.DispatchersProvider
import com.example.domain.model.Resource
import com.example.domain.model.Video
import com.example.domain.model.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface GetMovieVideoUseCase {
    fun getMovieVideoById(movieId: Int): Flow<Resource<Video>>
}

internal class GetMovieVideoUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository, private val dispatchersProvider: DispatchersProvider
) : GetMovieVideoUseCase {
    override fun getMovieVideoById(movieId: Int): Flow<Resource<Video>> = flow {
        try {
            emit(Resource.Loading())
            val videos = repository.getMovieVideo(movieId).mapToModel()
            emit(
                Resource.Success(videos)
            )
        } catch (exception: ApiException) {
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
    }.flowOn(dispatchersProvider.io())
}
