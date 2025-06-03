package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Notification
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotificationRepository {
    private val db = FirebaseFirestore.getInstance()
    private val notificationsRef = db.collection("notifications")

    fun sendNotification(notification: Notification): Task<Void> {
        return notificationsRef.document(notification.notificationId).set(notification)
    }

    fun getUserNotifications(userId: String): Query {
        return notificationsRef.whereEqualTo("userId", userId)
    }
}