package com.example.data.repository

import com.example.data.dto.ActorsDto
import com.example.data.dto.ResponseDto
import com.example.data.dto.VideoDto
import com.example.data.network.ApiException
import com.example.data.network.MoviesAPI
import retrofit2.HttpException
import java.io.IOException
import java.util.Date
import javax.inject.Inject

interface MoviesRepository {
    suspend fun getAllPopularMovies(currentPage: Int): ResponseDto
    suspend fun getTopRatedMovies(currentPage: Int): ResponseDto
    suspend fun getMoviesBasedOnFilters(
        currentPage: Int, lowestScore: Float? = null, highestScore: Float? = null,
        genre: String? = null,
        actors: String? = null,
        releaseDateLowest: String? = null, releaseDateHighest: String? = null,
        language: String? = null
    ): ResponseDto

    suspend fun getMoviesBasedOnKeyword(currentPage: Int, keyword: String): ResponseDto
    suspend fun getMovieVideo(movieId: Int): VideoDto
    suspend fun getMovieActors(movieId: Int): ActorsDto

}

internal class MoviesRepositoryImpl @Inject constructor(private val moviesAPI: MoviesAPI) :
    MoviesRepository {
    override suspend fun getAllPopularMovies(currentPage: Int): ResponseDto {
        return try {
            moviesAPI.getAllPopularMovies(currentPage)
        } catch (e: HttpException) {
            throw ApiException(message = "Could not retreive data. An unexpected error occured")
        } catch (e: IOException) {
            throw ApiException(
                noInternet = true,
                message = "Couldn't reach server. Check your internet connection."
            )
        }
    }

    override suspend fun getTopRatedMovies(currentPage: Int): ResponseDto {
        return try {
            moviesAPI.getTopRatedMovies(currentPage)
        } catch (e: HttpException) {
            throw ApiException(message = "Could not retreive data. An unexpected error occured")
        } catch (e: IOException) {
            throw ApiException(
                noInternet = true,
                message = "Couldn't reach server. Check your internet connection."
            )
        }
    }

    override suspend fun getMoviesBasedOnFilters(
        currentPage: Int,
        lowestScore: Float?,
        highestScore: Float?,
        genre: String?,
        actors: String?,
        releaseDateLowest: String?,
        releaseDateHighest: String?,
        language: String?
    ): ResponseDto {
        return try {
            moviesAPI.getMoviesBasedOnFilters(
                currentPage,
                releaseDateLowest,
                releaseDateHighest,
                lowestScore,
                highestScore,
                genre,
                actors,
                language
            )
        } catch (e: HttpException) {
            throw ApiException(message = "Could not retreive data. An unexpected error occured")
        } catch (e: IOException) {
            throw ApiException(
                noInternet = true,
                message = "Couldn't reach server. Check your internet connection."
            )
        }
    }

    override suspend fun getMoviesBasedOnKeyword(
        currentPage: Int,
        keyword: String
    ): ResponseDto {
        return try {
            moviesAPI.getMoviesBasedOnKeyword(currentPage, keyword)
        } catch (e: HttpException) {
            throw ApiException(message = "Could not retreive data. An unexpected error occured")
        } catch (e: IOException) {
            throw ApiException(
                noInternet = true,
                message = "Couldn't reach server. Check your internet connection."
            )
        }
    }

    override suspend fun getMovieVideo(movieId: Int): VideoDto {
        return try {
            moviesAPI.getMovieVideo(movieId)
        } catch (e: HttpException) {
            throw ApiException(message = "Could not retreive data. An unexpected error occured")
        } catch (e: IOException) {
            throw ApiException(
                noInternet = true,
                message = "Couldn't reach server. Check your internet connection."
            )
        }
    }

    override suspend fun getMovieActors(movieId: Int): ActorsDto {
        return try {
            moviesAPI.getMovieActors(movieId)
        } catch (e: HttpException) {
            throw ApiException(message = "Could not retreive data. An unexpected error occured")
        } catch (e: IOException) {
            throw ApiException(
                noInternet = true,
                message = "Couldn't reach server. Check your internet connection."
            )
        }
    }
}