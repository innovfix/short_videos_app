package com.app.reelshort.Model

data class ReasonsListResponse(
    val responseCode: String,
    val responseDetails: List<ResponseDetail>,
    val responseMessage: String,
)