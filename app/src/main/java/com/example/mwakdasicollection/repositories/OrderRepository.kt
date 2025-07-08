package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import com.example.mwakdasicollection.repositories.interfaces.OrderRepositoryInterface


class OrderRepository : OrderRepositoryInterface {
    private val db = FirebaseFirestore.getInstance()
    private val ordersRef = db.collection("orders")

    /**
     * Place a new order in Firestore.
     */
    override suspend fun placeOrder(order: Order): Result<Boolean> {
        return try {
            ordersRef.document(order.orderId).set(order).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get all orders for a specific user.
     */
    override suspend fun getUserOrders(userId: String): Result<List<Order>> {
        return try {
            val snapshot = ordersRef
                .whereEqualTo("userId", userId)
                .orderBy("orderDate", Query.Direction.DESCENDING)
                .get()
                .await()
            val orders = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Query orders for dynamic UI features like pagination.
     */
    override fun getUserOrdersQuery(userId: String): Query {
        return ordersRef
            .whereEqualTo("userId", userId)
            .orderBy("orderDate", Query.Direction.DESCENDING)
    }

    /**
     * Update the status of an existing order.
     */
    suspend fun updateOrderStatus(orderId: String, newStatus: String): Result<Boolean> {
        return try {
            ordersRef.document(orderId)
                .update("status", newStatus)
                .await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
