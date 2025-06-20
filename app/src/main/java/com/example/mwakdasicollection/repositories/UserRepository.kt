package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    /**
     * Fetch a user by their UID from Firestore.
     *
     * @param uid The unique identifier of the user.
     * @return A [Result] containing the [User] object if found, or an [Exception] on failure.
     */
    suspend fun getUser(uid: String): Result<User?> {
        return try {
            val snapshot = usersRef.document(uid).get().await()
            if (snapshot.exists()) {
                // Convert the snapshot to a User object
                val user = snapshot.toObject(User::class.java)
                Result.success(user) // Return the user object
            } else {
                Result.success(null) // User doesn't exist
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle and return exception
        }
    }
}