package com.example.mwakdasicollection.providers

import com.example.mwakdasicollection.model.Cart

// TODO to be deleted once samantha creates the real cart logic

interface CartProvider {
    /**
     * Retrieves the list of items currently in the cart.
     */
    suspend fun getCartItems(): List<Cart>
}