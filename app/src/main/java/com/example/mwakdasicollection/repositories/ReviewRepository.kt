package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ReviewRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    // Submit a review under products/{productId}/reviews
    suspend fun submitReview(productId: String, review: Review) {
        productsRef.document(productId)
            .collection("reviews")
            .add(review)
            .await()
    }

    // Fetch all reviews for a given productId as a Flow
    fun getReviews(productId: String): Flow<List<Review>> = flow {
        try {
            val snapshot = productsRef
                .document(productId)
                .collection("reviews")
                .get()
                .await()

            val reviews = snapshot.documents.mapNotNull { it.toObject(Review::class.java) }
            emit(reviews)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
