package com.app.reelshort.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SendOtpResponse(
    val code: String,
    val message: String,
)