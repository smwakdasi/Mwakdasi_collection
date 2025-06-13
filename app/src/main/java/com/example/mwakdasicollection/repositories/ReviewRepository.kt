package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ReviewRepository {
    private val db = FirebaseFirestore.getInstance()
    private val reviewsRef = db.collection("reviews")

    // Add a new review to Firestore
    fun addReview(review: Review, onComplete: (Boolean, Exception?) -> Unit) {
        reviewsRef.document(review.reviewId).set(review)
            .addOnSuccessListener {
                onComplete(true, null) // Review added successfully
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception) // Error occurred
            }
    }

    // Get reviews for a specific product
    fun getProductReviews(productId: String, onComplete: (List<Review>?, Exception?) -> Unit) {
        reviewsRef.whereEqualTo("productId", productId)
            .orderBy("timestamp", Query.Direction.DESCENDING) // Sort by timestamp
            .get()
            .addOnSuccessListener { querySnapshot ->
                val reviews = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Review::class.java)
                }
                onComplete(reviews, null) // Successfully retrieved reviews
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Handle errors
            }
    }

    // Optional query-based method for paginated reviews
    fun getProductReviewsQuery(productId: String): Query {
        return reviewsRef
            .whereEqualTo("productId", productId)
            .orderBy("timestamp", Query.Direction.DESCENDING) // Sort by most recent
    }
}