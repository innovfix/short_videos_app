package com.app.reelshort.Model

import com.google.gson.annotations.SerializedName

data class PremiumPlansResponse(
    val success: Boolean,
    val message: String?,
    @SerializedName("premium_plans")
    val premiumPlans: List<PremiumPlans>?,
)



data class PremiumPlans (
    val title: String,
    @SerializedName("current_cost")
    val currentCost: Int,
    @SerializedName("original_cost")
    val originalCost: Int,
    val term: String,
    val offer: String,
    var isSelected:Boolean = false,
)