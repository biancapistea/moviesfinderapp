package com.example.domain.usecases

import com.example.data.network.ApiException
import com.example.data.repository.MoviesRepository
import com.example.data.service.DispatchersProvider
import com.example.domain.mappers.mapGenreStringToId
import com.example.domain.mappers.mapLanguageToPrefix
import com.example.domain.model.ListMovies
import com.example.domain.model.Resource
import com.example.domain.model.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetMoviesBasedOnFiltersUseCase {
    fun getMoviesBasedOnFilters(
        currentPage: Int,
        lowestScore: Float? = null,
        highestScore: Float? = null,
        genres: List<String>? = null,
        actors: String? = null,
        releaseDateLowest: String? = null,
        releaseDateHighest: String? = null,
        language: String? = null,
    ): Flow<Resource<ListMovies>>
}

internal class GetMoviesBasedOnFiltersUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository, private val dispatchersProvider: DispatchersProvider
) :
    GetMoviesBasedOnFiltersUseCase {
    override fun getMoviesBasedOnFilters(
        currentPage: Int, lowestScore: Float?, highestScore: Float?,
        genres: List<String>?,
        actors: String?,
        releaseDateLowest: String?,
        releaseDateHighest: String?,
        language: String?
    ): Flow<Resource<ListMovies>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getMoviesBasedOnFilters(
                currentPage,
                lowestScore,
                highestScore,
                genres?.map { genreString -> mapGenreStringToId(genreString) }?.joinToString(),
                actors,
                releaseDateLowest = "${releaseDateLowest}-01-01",
                releaseDateHighest = "${releaseDateHighest}-12-30",
                language = language?.let { mapLanguageToPrefix(it) }
            ).mapToModel()
            emit(
                Resource.Success(movies)
            )
        } catch (exception: ApiException) {
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
    }.flowOn(dispatchersProvider.io())
}