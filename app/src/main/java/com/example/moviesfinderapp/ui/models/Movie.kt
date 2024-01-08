package com.example.moviesfinderapp.ui.models

import android.os.Parcelable
import com.example.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUi(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double,
    val description: String,
    val genres: List<String>,
    val releaseDate: String?,
): Parcelable

fun Movie.toComposeUiModel(): MovieUi {
    return MovieUi(
        id = id,
        title = title,
        imageUrl = posterPath ?: "",
        rating = voteAverage,
        releaseDate = releaseDate ?: "",
        genres = genres,
        description = overview
    )
}