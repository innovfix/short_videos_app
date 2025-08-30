package com.app.reelshort.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class LoginRequest(
    @SerializedName("mobile_number")
    val mobile: String,
)


