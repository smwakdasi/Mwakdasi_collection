package com.example.mwakdasicollection.model

data class WishlistItem(
    val userId: String = "", // ID of the user who added the item to their wishlist
    val productId: String = "" // ID of the product in the wishlist
) {

    // Checks if the WishlistItem object has valid non-empty userId and productId
    fun isValid(): Boolean {
        return userId.isNotBlank() && productId.isNotBlank()
    }

    // Provides a user-friendly summary of the wishlist item
    fun getItemSummary(): String {
        return "WishlistItem(User ID: $userId, Product ID: $productId)"
    }
}