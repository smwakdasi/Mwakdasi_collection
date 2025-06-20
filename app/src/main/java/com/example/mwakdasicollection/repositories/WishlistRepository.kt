package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.WishlistItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WishlistRepository {
    private val db = FirebaseFirestore.getInstance()
    private val wishlistRef = db.collection("wishlists")

    /**
     * Add an item to the wishlist.
     *
     * @param item The [WishlistItem] to be added.
     * @return A [Result] containing `true` on success, or an [Exception] on failure.
     */
    suspend fun addToWishlist(item: WishlistItem): Result<Boolean> {
        val docId = "${item.userId}_${item.productId}"
        return try {
            wishlistRef.document(docId).set(item).await()
            Result.success(true) // Successfully added
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Fetch the wishlist for a specific user.
     *
     * @param userId The ID of the user whose wishlist is being fetched.
     * @return A [Result] containing a list of [WishlistItem] on success, or an [Exception] on failure.
     */
    suspend fun getUserWishlist(userId: String): Result<List<WishlistItem>> {
        return try {
            val snapshot = wishlistRef.whereEqualTo("userId", userId).get().await()
            val wishlist = snapshot.documents.mapNotNull { document ->
                document.toObject(WishlistItem::class.java)
            }
            Result.success(wishlist) // Successfully retrieved the wishlist
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Check if an item exists in the user's wishlist.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product being checked.
     * @return A [Result] containing `true` if the item exists, `false` otherwise, or an [Exception] on failure.
     */
    suspend fun isItemInWishlist(userId: String, productId: String): Result<Boolean> {
        val docId = "${userId}_${productId}"
        return try {
            val document = wishlistRef.document(docId).get().await()
            Result.success(document.exists()) // Return true if the document exists
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Remove an item from the user's wishlist.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product being removed.
     * @return A [Result] containing `true` on successful removal, or an [Exception] on failure.
     */
    suspend fun removeFromWishlist(userId: String, productId: String): Result<Boolean> {
        val docId = "${userId}_${productId}"
        return try {
            wishlistRef.document(docId).delete().await()
            Result.success(true) // Successfully removed the item
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }
}