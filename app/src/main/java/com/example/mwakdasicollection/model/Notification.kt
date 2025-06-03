package com.example.mwakdasicollection.model

data class Notification(
    val notificationId: String = "",
    val userId: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
