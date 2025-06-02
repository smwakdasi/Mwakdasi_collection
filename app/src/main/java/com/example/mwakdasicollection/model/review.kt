package com.example.mwakdasicollection.model

data class Review(
    val reviewId: String = "",
    val productId: String = "",
    val userId: String = "",
    val rating: Float = 0.0f,
    val comment: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
