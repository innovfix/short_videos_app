package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class PremiumPlansUsersResponse(
    val success: Boolean,
    val message: String?,
    @SerializedName("premium_plans_users")
    val premiumPlansUsers: PremiumPlansUsers?,
)



data class PremiumPlansUsers (
    @SerializedName("users")
    val users: Int,
)