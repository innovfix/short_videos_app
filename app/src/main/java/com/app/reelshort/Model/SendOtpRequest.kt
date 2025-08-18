package com.app.reelshort.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class SendOtpRequest(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("otp")
    val otp: String,
)


