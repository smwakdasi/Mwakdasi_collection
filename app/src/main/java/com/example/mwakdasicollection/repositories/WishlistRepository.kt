package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.WishlistItem
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class WishlistRepository {
    private val db = FirebaseFirestore.getInstance()
    private val wishlistRef = db.collection("wishlists")

    fun addToWishlist(item: WishlistItem): Task<Void> {
        val docId = "${item.userId}_${item.productId}"
        return wishlistRef.document(docId).set(item)
    }

    fun getUserWishlist(userId: String): Query {
        return wishlistRef.whereEqualTo("userId", userId)
    }
}