package com.app.reelshort.Model

data class ResponseDetailX(
    val answer: String,
    val created_at: String,
    val id: Int,
    val question: String,
    val updated_at: Any,
    var isExpanded: Boolean = false, // for dropdown toggle
)