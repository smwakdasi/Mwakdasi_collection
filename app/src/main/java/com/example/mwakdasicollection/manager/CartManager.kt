package com.example.mwakdasicollection.manager

import androidx.compose.runtime.mutableStateListOf
import com.example.mwakdasicollection.model.Cart

object CartManager {
    // Using mutableStateListOf makes it observable in Compose UIs.
    val cartItems = mutableStateListOf<Cart>()

    fun addToCart(newItem: Cart) {
        val existingItem = cartItems.find { it.productId == newItem.productId }
        if (existingItem != null) {
            updateQuantity(existingItem, existingItem.quantity + newItem.quantity)
        } else {
            cartItems.add(newItem)
        }
    }

    fun updateQuantity(item: Cart, newQuantity: Int) {
        val index = cartItems.indexOfFirst { it.productId == item.productId }
        if (index != -1) {
            if (newQuantity > 0) {
                cartItems[index] = item.copy(quantity = newQuantity)
            } else {
                cartItems.removeAt(index)
            }
        }
    }

    fun removeItem(item: Cart) {
        cartItems.remove(item)
    }

    fun getTotalCost() = cartItems.sumOf { it.price * it.quantity }
    fun getTotalItems() = cartItems.sumOf { it.quantity }
}