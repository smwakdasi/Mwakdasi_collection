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

    /**
     * Updates the quantity of a specific item in the user's cart.
     *
     * @param userId The ID of the user whose cart is being updated.
     * @param itemId The ID of the item whose quantity needs to be updated.
     * @param newQuantity The updated quantity value for the item.
     * @return A [Result] indicating the success or failure of the update operation.
     */
    suspend fun updateCartItemQuantity(userId: String, itemId: String, newQuantity: Int): Result<Unit> {
        return try {
            val cartDocument = cartsRef.document(userId)
            val updates = mapOf("items.$itemId.quantity" to newQuantity) // Nested field update
            cartDocument.update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Removes an item from the user's cart based on the item ID.
     *
     * @param userId The ID of the user whose cart is being updated.
     * @param itemId The ID of the item to be removed from the cart.
     * @return A [Result] indicating the success or failure of the remove operation.
     */
    suspend fun removeCartItem(userId: String, itemId: String): Result<Unit> {
        return try {
            val cartDocument = cartsRef.document(userId)
            // Use Firestore's FieldValue.delete() for removing the field
            cartDocument.update("items.$itemId", null).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetches all cart items for the given user.
     *
     * @param userId The ID of the user whose cart items are to be fetched.
     * @return A [Result] containing a list of cart items or an [Exception] on failure.
     */
    suspend fun getCartItems(userId: String): Result<List<Cart.Item>> {
        return try {
            val documentSnapshot = cartsRef.document(userId).get().await()
            if (documentSnapshot.exists()) {
                val cart = documentSnapshot.toObject<Cart>()
                Result.success(cart?.items ?: emptyList())
            } else {
                Result.success(emptyList()) // No items found
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}