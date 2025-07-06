package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ReviewRepository(firestore: FirebaseFirestore) {
//    private val db = FirebaseFirestore.getInstance()
    private val productsRef = firestore.collection("PRODUCTS")

    // Submit a review under products/{productId}/reviews
    suspend fun submitReview(productId: String, review: Review) {
        productsRef.document(productId)
            .collection("reviews")
            .add(review)
            .await()
    }

    // Fetch all reviews for a given productId as a Flow
    fun getReviews(productId: String): Flow<List<Review>> = callbackFlow {
    val subscription = productsRef.document(productId)
        .collection("reviews")
        .orderBy("timestamp") // Order by timestamp to get the latest reviews first
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val reviews = snapshot?.documents?.mapNotNull { it.toObject(Review::class.java) } ?: emptyList()
            trySend(reviews)
        }
    awaitClose { subscription.remove() }
}
}