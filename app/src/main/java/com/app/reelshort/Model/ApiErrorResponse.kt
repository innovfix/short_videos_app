package com.app.reelshort.Model

import androidx.annotation.Keep
import com.app.reelshort.Model.UnlockEpisodeResponse.ResponseDetails
import com.google.gson.annotations.SerializedName

@Keep
data class ApiErrorResponse(
    @SerializedName("responseCode")
    val responseCode: String?,
    @SerializedName("responseDetails")
    val responseDetails: ResponseDetails?,
    @SerializedName("responseMessage")
    val responseMessage: String?,
)