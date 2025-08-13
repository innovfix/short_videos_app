package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

interface CommonInfoBase {
    val id: Int?
    val title: String?
    val tagsName: String?
    val videoUrl: String?
    val views: Int?
    val thumbnail: String?
    val description: String?
    val isLiked: Int
    val isFavourites: Int

    val seriesId: Int?
    val episodeNumber: Int?
    val freeEpisodes: Int?
    val thumbnailUrl: String?
    val categoryName: String?
    val coverVideo: String?
    val poster: String?

    val isLocked: Int
    val isFree: Int?
}
