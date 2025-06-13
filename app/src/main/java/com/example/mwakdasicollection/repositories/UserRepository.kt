package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    // Fetch user by UID
    fun getUser(uid: String, onComplete: (User?, Exception?) -> Unit) {
        usersRef.document(uid).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val user = snapshot.toObject(User::class.java)
                    onComplete(user, null) // Successfully retrieved user
                } else {
                    onComplete(null, null) // User does not exist
                }
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Handle Firestore error
            }
    }
}