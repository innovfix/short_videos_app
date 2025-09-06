package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class ShortsResponse(
    val success: Boolean,
    val shorts: List<Shorts>?,
)

data class Shorts(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("video_url")
    val videoUrl: String,
)