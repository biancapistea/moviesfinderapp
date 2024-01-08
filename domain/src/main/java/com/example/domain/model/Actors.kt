package com.example.domain.model

import com.example.data.dto.ActorsDto
import com.example.data.dto.ActorsResultDto

data class Actors(
    val cast: List<ActorsResult>,
    val id: Int
)

data class ActorsResult(
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

fun ActorsDto.mapToModel(): Actors {
    return Actors(id = id, cast = cast.map { it.mapToModel() })
}

fun ActorsResultDto.mapToModel(): ActorsResult {
    return ActorsResult(
        adult,
        character,
        gender,
        id,
        knownForDepartment,
        name,
        order,
        originalName,
        popularity,
        profilePath = if (profilePath != null) {
            transformIntoImageUrl(profilePath)
        } else ""
    )
}