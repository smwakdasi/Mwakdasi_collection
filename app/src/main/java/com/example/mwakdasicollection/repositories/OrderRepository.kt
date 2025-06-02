package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OrderRepository {
    private val db = FirebaseFirestore.getInstance()
    private val ordersRef = db.collection("orders")

    fun placeOrder(order: Order): Task<Void> {
        return ordersRef.document(order.orderId).set(order)
    }

    fun getUserOrders(userId: String): Query {
        return ordersRef.whereEqualTo("userId", userId)
    }
}