package com.example.mwakdasicollection.repositories.interfaces

import com.example.mwakdasicollection.model.Cart
import com.example.mwakdasicollection.model.CartItem

interface CartRepositoryInterface {
    suspend fun getUserCart(userId: String): Result<Cart?>
    suspend fun addToCart(userId: String, item: CartItem): Result<Boolean>
    suspend fun removeFromCart(userId: String, productId: String): Result<Boolean>
    suspend fun getCartItems(userId: String): Result<List<CartItem>>
    suspend fun clearCart(userId: String): Result<Boolean>
}
