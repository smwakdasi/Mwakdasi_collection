package com.example.mwakdasicollection.model

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val items: List<CartItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val status: String = "pending", // e.g., pending, shipped, delivered
    val orderDate: Long = System.currentTimeMillis()
) {
    annotation class CartItem
}
