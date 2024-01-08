package com.example.data.network

import com.example.data.dto.ActorsDto
import com.example.data.dto.ResponseDto
import com.example.data.dto.VideoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MoviesAPI {

    @GET("discover/movie?include_adult=false&include_video=true&language=en-US&sort_by=popularity.desc&with_original_language=en")
    suspend fun getAllPopularMovies(@Query("page") currentPage: Int = 1): ResponseDto

    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&vote_average.gte=8.3&vote_average.lte=9.5&with_original_language=en")
    suspend fun getTopRatedMovies(@Query("page") currentPage: Int = 1): ResponseDto

    @GET("discover/movie?include_adult=false&include_video=true&language=en-US&sort_by=popularity.desc")
    suspend fun getMoviesBasedOnFilters(
        @Query("page") currentPage: Int = 1,
        @Query("release_date.gte") releaseDateLowest: String?,
        @Query("release_date.lte") releaseDateHighest: String?,
        @Query("vote_average.gte") lowestScore: Float?,
        @Query("vote_average.lte") highestScore: Float?,
        @Query("with_genres") genre: String?,
        @Query("with_people") actors: String?,
        @Query("with_original_language") language: String?
    ): ResponseDto

    @GET("search/movie?include_adult=false&language=en-US")
    suspend fun getMoviesBasedOnKeyword(
        @Query("page") currentPage: Int = 1,
        @Query("query") keyword: String
    ): ResponseDto

    @GET("movie/{movie_id}/videos?language=en-US")
    suspend fun getMovieVideo(@Path("movie_id") movieId: Int): VideoDto

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieActors(@Path("movie_id") movieId: Int): ActorsDto
}