package com.example.mwakdasicollection.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotificationRepository {
    private val db = FirebaseFirestore.getInstance()
    private val notificationsRef = db.collection("notifications")

    // Sends a new notification by saving it to Firestore
    fun sendNotification(notification: Notification, onComplete: (Boolean, Exception?) -> Unit) {
        notificationsRef.document(notification.notificationId).set(notification)
            .addOnSuccessListener {
                onComplete(true, null) // Notification sent successfully
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception) // Handle Firestore errors
            }
    }

    // Fetches user notifications based on userId
    fun getUserNotifications(userId: String, onComplete: (List<Notification>?, Exception?) -> Unit) {
        notificationsRef.whereEqualTo("userId", userId).get()
            .addOnSuccessListener { querySnapshot ->
                val notifications = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Notification::class.java) // Deserialize documents into Notification objects
                }
                onComplete(notifications, null) // Return the list of notifications
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Return the error if the query fails
            }
    }

    // Fetches user notifications with sorting and limit using a Query
    fun getUserNotificationsQuery(userId: String, limit: Long): Query {
        return notificationsRef
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING) // Sort notifications by timestamp
            .limit(limit) // Limit the number of returned notifications
    }
}