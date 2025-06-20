package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Cart
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class CartRepository {
    private val db = FirebaseFirestore.getInstance()
    private val cartsRef = db.collection("carts")

    /**
     * Fetches the user's cart document from Firestore using `userId`.
     *
     * @param userId The ID of the user whose cart is to be fetched.
     * @return A [Result] containing the [Cart] object on success or an [Exception] on failure.
     */
    suspend fun getUserCart(userId: String): Result<Cart?> {
        return try {
            val documentSnapshot = cartsRef.document(userId).get().await()
            if (documentSnapshot.exists()) {
                val cart = documentSnapshot.toObject<Cart>() // Deserialize into Cart object
                Result.success(cart)
            } else {
                Result.success(null) // No document found
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle exceptions
        }
    }
}