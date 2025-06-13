package com.example.mwakdasicollection.model

import java.text.SimpleDateFormat
import java.util.*

data class Review(
    val reviewId: String = "",
    val productId: String = "",
    val userId: String = "",
    val rating: Float = 0.0f, // Represents product rating out of 5 (e.g., 4.5)
    val comment: String = "", // User's review comment about the product
    val timestamp: Long = System.currentTimeMillis() // Timestamp when the review was created
) {

    // Validates if the review rating is within the acceptable range (1.0 to 5.0)
    fun isValidRating(): Boolean {
        return rating in 1.0f..5.0f
    }

    // Converts the timestamp into a human-readable format
    fun getFormattedTimestamp(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    // Checks if the comment is meaningful or empty
    fun hasComment(): Boolean {
        return comment.isNotBlank()
    }
}