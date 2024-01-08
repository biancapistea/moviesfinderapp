package com.example.data.dto

import com.squareup.moshi.Json

data class ActorsDto(
    val cast: List<ActorsResultDto>,
    val id: Int
)

data class ActorsResultDto(
    val adult: Boolean,
    val character: String,
    val gender: Int,
    val id: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
)