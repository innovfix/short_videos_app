package com.app.reelshort.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StripePaymentIntentResponse(
    @SerializedName("responseCode")
    val responseCode: String?,
    @SerializedName("responseDetails")
    val responseDetails: String?,
    @SerializedName("responseMessage")
    val responseMessage: String?,
)