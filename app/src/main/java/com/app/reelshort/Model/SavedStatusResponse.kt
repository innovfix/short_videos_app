package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class SavedStatusResponse(
    val success: Boolean?,
    @SerializedName("is_listed")
    val isListed: Boolean?,
)