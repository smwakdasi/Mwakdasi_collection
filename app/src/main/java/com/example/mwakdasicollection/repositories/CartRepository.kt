package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Cart
import com.example.mwakdasicollection.model.CartItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import com.example.mwakdasicollection.repositories.interfaces.CartRepositoryInterface


class CartRepository : CartRepositoryInterface {
    private val db = FirebaseFirestore.getInstance()
    private val cartsRef = db.collection("carts")

    override suspend fun getUserCart(userId: String): Result<Cart?> {
        return try {
            val documentSnapshot = cartsRef.document(userId).get().await()
            if (documentSnapshot.exists()) {
                val cart = documentSnapshot.toObject<Cart>()
                Result.success(cart)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Add or update an item in the user's cart.
     */
    override suspend fun addToCart(userId: String, item: CartItem): Result<Boolean> {
        return try {
            val itemRef = cartsRef.document(userId)
                .collection("items")
                .document(item.productId)
            itemRef.set(item).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Remove a specific item from the user's cart.
     */
    override suspend fun removeFromCart(userId: String, productId: String): Result<Boolean> {
        return try {
            cartsRef.document(userId)
                .collection("items")
                .document(productId)
                .delete()
                .await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get all cart items for the specified user.
     */
    override suspend fun getCartItems(userId: String): Result<List<CartItem>> {
        return try {
            val snapshot = cartsRef.document(userId)
                .collection("items")
                .get()
                .await()
            val items = snapshot.documents.mapNotNull { it.toObject(CartItem::class.java) }
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Clear all items in a user's cart.
     */
    override suspend fun clearCart(userId: String): Result<Boolean> {
        return try {
            val itemsSnapshot = cartsRef.document(userId).collection("items").get().await()
            for (doc in itemsSnapshot.documents) {
                doc.reference.delete().await()
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
