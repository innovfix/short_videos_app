package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class PremiumPlansVideoResponse(
    val success: Boolean,
    val message: String?,
    @SerializedName("premium_plans_video")
    val premiumPlansVideo: PremiumPlansVideo?,
)

data class PremiumPlansVideo (
    @SerializedName("video_url")
    val videoUrl: String,
)