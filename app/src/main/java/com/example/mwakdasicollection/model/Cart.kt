package com.example.mwakdasicollection.model

data class Cart(
    val productId: String = "",
    val productName: String = "", // Added product name
    val quantity: Int = 0,
    val price: Double = 0.0,
    val imageUrl: String = "" // Added product image URL
) {

    // Calculates the total cost of a product in the cart
    fun getTotalCost(): Double {
        return price * quantity
    }

    // Checks if the cart item is valid
    fun isValidCartItem(): Boolean {
        return productId.isNotBlank() && quantity > 0 && price >= 0.0
    }


}