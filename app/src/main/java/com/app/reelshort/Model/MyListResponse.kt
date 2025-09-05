package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class MyListResponse(
    val success: Boolean,
    val message: String?,
    @SerializedName("my_list")
    val myList: List<Shorts>?,
)