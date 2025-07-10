package com.example.mwakdasicollection.model

import java.text.SimpleDateFormat
import java.util.*

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val items: List<Cart> = emptyList(), // Represents the list of items in the order
    val totalAmount: Double = items.sumOf { it.price * it.quantity }, // Calculates total dynamically
    val status: String = "pending", // e.g., pending, shipped, delivered, canceled
    val orderDate: Long = System.currentTimeMillis() // Order creation timestamp
) {

    // Returns a human-readable formatted order date
    fun getFormattedOrderDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(orderDate))
    }

    // Returns the number of items in the order (ignoring quantities)
    fun getItemCount(): Int {
        return items.size
    }

    // Returns the total quantity of items ordered
    fun getTotalQuantity(): Int {
        return items.sumOf { it.quantity }
    }
}

// Represents a single item in the cart/order
data class CartItem(
    val productId: String = "",
    val productName: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0
)