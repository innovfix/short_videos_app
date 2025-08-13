package com.app.reelshort.Model

data class FqaResponse(
    val responseCode: String,
    val responseDetails: List<ResponseDetailX>,
    val responseMessage: String,
)