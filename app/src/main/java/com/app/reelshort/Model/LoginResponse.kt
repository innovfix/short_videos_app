package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val data: UserData?,
)



data class UserData (
    val id: Int,
    val name: String,
    @SerializedName("user_gender")
    val gender: String,
    val image: String,
    val language: String,
    val mobile: String,
)