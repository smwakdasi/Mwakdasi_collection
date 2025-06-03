package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Review
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ReviewRepository {
    private val db = FirebaseFirestore.getInstance()
    private val reviewsRef = db.collection("reviews")

    fun addReview(review: Review): Task<Void> {
        return reviewsRef.document(review.reviewId).set(review)
    }

    fun getProductReviews(productId: String): Query {
        return reviewsRef.whereEqualTo("productId", productId)
    }
}