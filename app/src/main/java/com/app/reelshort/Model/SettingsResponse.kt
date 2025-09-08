package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class SettingsResponse(
    val success: Boolean?,
    val message: String?,
    val data: ArrayList<SettingsResponseData>?,
)

data class SettingsResponseData(
    val id: Int?,
    @SerializedName("privacy_and_policy")
    val privacyPolicy: String?,
    @SerializedName("support_mail")
    val supportMail: String?,
    @SerializedName("terms_and_conditions")
    val termsAndConditions: String?,
)