package com.example.domain.model

import com.example.data.dto.VideoDto
import com.example.data.dto.VideoResultsDto

data class Video(
    val id: Int,
    val results: List<VideoResults>
)

data class VideoResults(
    val id: String,
    val videoUrl: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val type: String
)

fun transformIntoVideoUrl(key: String?): String {
    return "${"http://youtube.com/watch?v="}$key"
}

fun VideoDto.mapToModel(): Video {
    return Video(id = id, results = results.map { it.mapToModel() })
}

fun VideoResultsDto.mapToModel(): VideoResults {
    return VideoResults(
        id = id,
        videoUrl = key,
        name = name,
        official = official,
        publishedAt = publishedAt,
        type = type
    )
}