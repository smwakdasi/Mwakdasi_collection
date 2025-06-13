package com.example.mwakdasicollection.model

import java.text.SimpleDateFormat
import java.util.*

data class Notification(
    val notificationId: String = "",
    val userId: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val title: String = "New Notification" // Added notification title
) {

    // Returns a formatted timestamp as a readable date-time string
    fun getFormattedTimestamp(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    // Marks the notification as read by copying it with isRead = true
    fun markAsRead(): Notification {
        return this.copy(isRead = true)
    }

    // Checks if the notification is unread
    fun isUnread(): Boolean {
        return !isRead
    }
}