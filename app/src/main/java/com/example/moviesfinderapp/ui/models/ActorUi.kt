package com.example.moviesfinderapp.ui.models

import com.example.domain.model.ActorsResult

data class ActorUi(
    val adult: Boolean,
    val character: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)

fun ActorsResult.mapToComposeUi(): ActorUi {
    return ActorUi(
        adult,
        character,
        gender,
        id,
        knownForDepartment,
        name,
        order,
        originalName,
        popularity,
        profilePath = profilePath ?: ""
    )
}