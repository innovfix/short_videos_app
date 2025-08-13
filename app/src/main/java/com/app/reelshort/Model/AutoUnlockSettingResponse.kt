package com.app.reelshort.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AutoUnlockSettingResponse(
    @SerializedName("responseCode")
    val responseCode: String?,
    @SerializedName("responseMessage")
    val responseMessage: String?,
)