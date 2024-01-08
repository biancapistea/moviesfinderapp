package com.example.moviesfinderapp.ui.models

import android.os.Parcelable
import com.example.domain.model.VideoResults
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoUi(
    val id: String,
    val videoUrl: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val type: String
):Parcelable

fun VideoResults.toComposeUiModel(): VideoUi {
    return VideoUi(
        id = id,
        videoUrl = videoUrl,
        name = name,
        official = official,
        publishedAt = publishedAt,
        type = type
    )
}