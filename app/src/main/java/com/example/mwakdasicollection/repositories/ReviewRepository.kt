package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ReviewRepository {
    private val db = FirebaseFirestore.getInstance()
    private val reviewsRef = db.collection("reviews")

    /**
     * Adds a new review to Firestore.
     *
     * @param review The [Review] object to be added.
     * @return A [Result] encapsulating success (true) or an [Exception] on failure.
     */
    suspend fun addReview(review: Review): Result<Boolean> {
        return try {
            reviewsRef.document(review.reviewId).set(review).await()
            Result.success(true) // Review successfully added
        } catch (e: Exception) {
            Result.failure(e) // Handle exception
        }
    }

    /**
     * Fetches all reviews associated with a specific product.
     *
     * @param productId The ID of the product for which reviews are fetched.
     * @return A [Result] containing a list of [Review] objects if successful, or an [Exception] on failure.
     */
    suspend fun getReviewsByProductId(productId: String): Result<List<Review>> {
        return try {
            val querySnapshot = reviewsRef
                .whereEqualTo("productId", productId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            val reviews = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Review::class.java)
            }
            Result.success(reviews) // Successfully fetched reviews
        } catch (e: Exception) {
            Result.failure(e) // Handle exception
        }
    }

    /**
     * Provides a Query for paginated reviews of a specific product.
     *
     * @param productId The ID of the product for which a paginated query of reviews is required.
     * @return A [Query] object for Firestore to fetch reviews with pagination support.
     */
    fun getProductReviewsQuery(productId: String): Query {
        return reviewsRef
            .whereEqualTo("productId", productId)
            .orderBy("timestamp", Query.Direction.DESCENDING) // Sort by most recent reviews
    }
}