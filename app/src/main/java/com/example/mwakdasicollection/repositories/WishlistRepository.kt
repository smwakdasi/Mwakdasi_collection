package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.WishlistItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class WishlistRepository {
    private val db = FirebaseFirestore.getInstance()
    private val wishlistRef = db.collection("wishlists")

    // Add an item to the wishlist
    fun addToWishlist(item: WishlistItem, onComplete: (Boolean, Exception?) -> Unit) {
        val docId = "${item.userId}_${item.productId}"
        wishlistRef.document(docId).set(item)
            .addOnSuccessListener {
                onComplete(true, null) // Success
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception) // Error
            }
    }

    // Get the wishlist for a specific user
    fun getUserWishlist(userId: String, onComplete: (List<WishlistItem>?, Exception?) -> Unit) {
        wishlistRef.whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val wishlist = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(WishlistItem::class.java)
                }
                onComplete(wishlist, null) // Success
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Error
            }
    }

    // Check if an item exists in the wishlist
    fun isItemInWishlist(userId: String, productId: String, onComplete: (Boolean, Exception?) -> Unit) {
        val docId = "${userId}_${productId}"
        wishlistRef.document(docId).get()
            .addOnSuccessListener { document ->
                onComplete(document.exists(), null) // Return `true` if the item exists
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception) // Error
            }
    }

    // Remove an item from the wishlist
    fun removeFromWishlist(userId: String, productId: String, onComplete: (Boolean, Exception?) -> Unit) {
        val docId = "${userId}_${productId}"
        wishlistRef.document(docId).delete()
            .addOnSuccessListener {
                onComplete(true, null) // Successfully removed item
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception) // Error
            }
    }
}