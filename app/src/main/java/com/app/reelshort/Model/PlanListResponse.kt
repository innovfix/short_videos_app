package com.app.reelshort.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PlanListResponse(
    @SerializedName("responseCode")
    val responseCode: String?,
    @SerializedName("responseDetails")
    val responseDetails: ResponseDetails?,
    @SerializedName("responseMessage")
    val responseMessage: String?,
) {
    @Keep
    data class ResponseDetails(
        @SerializedName("limited")
        val limited: List<Limited?>?,
        @SerializedName("unlimited")
        val unlimited: List<Unlimited?>?,
    ) {
        @Keep
        data class Limited(
            @SerializedName("amount")
            val amount: String?,
            @SerializedName("coin")
            val coin: String?,
            @SerializedName("created_at")
            val createdAt: String?,
            @SerializedName("description")
            val description: Any?,
            @SerializedName("discount_percentage")
            val discountPercentage: String?,
            @SerializedName("extra_coin")
            val extraCoin: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("is_deleted")
            val isDeleted: Int?,
            @SerializedName("is_limited_time")
            val isLimitedTime: Int?,
            @SerializedName("is_unlimited")
            val isUnlimited: Int?,
            @SerializedName("is_weekly")
            val isWeekly: Int?,
            @SerializedName("is_yearly")
            val isYearly: Int?,
            @SerializedName("name")
            val name: Any?,
            @SerializedName("updated_at")
            val updatedAt: String?,
        )

        @Keep
        data class Unlimited(
            @SerializedName("amount")
            val amount: String?,
            @SerializedName("coin")
            val coin: Any?,
            @SerializedName("created_at")
            val createdAt: String?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("discount_percentage")
            val discountPercentage: Any?,
            @SerializedName("extra_coin")
            val extraCoin: Any?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("is_deleted")
            val isDeleted: Int?,
            @SerializedName("is_limited_time")
            val isLimitedTime: Int?,
            @SerializedName("is_unlimited")
            val isUnlimited: Int?,
            @SerializedName("is_weekly")
            val isWeekly: Int?,
            @SerializedName("is_yearly")
            val isYearly: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("updated_at")
            val updatedAt: String?,
        )
    }
}