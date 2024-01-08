package com.example.domain.model

import com.example.data.dto.MovieDto
import com.example.data.dto.ResponseDto
import com.example.domain.mappers.mapGenreIdToString
import com.example.domain.mappers.mapPrefixToLanguage

data class ListMovies(
    val page: Int,
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val backdropPath: String?,
    val genres: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
)

fun transformIntoImageUrl(string: String?): String {
    return "${"https://image.tmdb.org/t/p/w500/"}$string"
}

fun ResponseDto.mapToModel(): ListMovies {
    return ListMovies(page = page, results = results.map { it.mapToModel() })
}

fun MovieDto.mapToModel(): Movie {
    return Movie(
        id = id,
        backdropPath = backdropPath,
        genres = genreIds.map { id ->
            mapGenreIdToString(id)
        },
        originalLanguage = mapPrefixToLanguage(originalLanguage),
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = transformIntoImageUrl(posterPath),
        releaseDate = if (releaseDate?.isNotEmpty() == true) {
            releaseDate?.substring(0, 4) ?: ""
        } else {
            releaseDate ?: ""
        },
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}