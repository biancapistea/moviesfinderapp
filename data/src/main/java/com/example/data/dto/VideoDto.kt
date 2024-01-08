package com.example.data.dto

import com.squareup.moshi.Json

data class VideoDto(
    val id: Int,
    val results: List<VideoResultsDto>
)

data class VideoResultsDto(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @Json(name = "published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)