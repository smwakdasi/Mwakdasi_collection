package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OrderRepository {
    private val db = FirebaseFirestore.getInstance()
    private val ordersRef = db.collection("orders")

    // Place an order
    fun placeOrder(order: Order, onComplete: (Boolean, Exception?) -> Unit) {
        ordersRef.document(order.orderId).set(order)
            .addOnSuccessListener {
                onComplete(true, null) // Order placed successfully
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception) // Handle errors
            }
    }

    // Get orders for a specific user
    fun getUserOrders(userId: String, onComplete: (List<Order>?, Exception?) -> Unit) {
        ordersRef.whereEqualTo("userId", userId)
            .orderBy("orderDate", Query.Direction.DESCENDING) // Order by date, latest first
            .get()
            .addOnSuccessListener { querySnapshot ->
                val orders = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Order::class.java)
                }
                onComplete(orders, null) // Successfully fetched orders
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Handle errors
            }
    }

    // Query for dynamic interaction (pagination, etc.)
    fun getUserOrdersQuery(userId: String): Query {
        return ordersRef
            .whereEqualTo("userId", userId)
            .orderBy("orderDate", Query.Direction.DESCENDING) // Sort orders by most recent
    }
}