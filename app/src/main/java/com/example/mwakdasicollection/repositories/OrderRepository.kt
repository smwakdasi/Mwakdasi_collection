package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class OrderRepository {
    private val db = FirebaseFirestore.getInstance()
    private val ordersRef = db.collection("orders")

    /**
     * Places an order.
     *
     * @param order The [Order] object to be placed in the Firestore.
     * @return A [Result] indicating success or failure.
     */
    suspend fun placeOrder(order: Order): Result<Boolean> {
        return try {
            ordersRef.document(order.orderId).set(order).await()
            Result.success(true) // Order placed successfully
        } catch (e: Exception) {
            Result.failure(e) // Handle any exception during the operation
        }
    }

    /**
     * Fetches all orders for a specific user.
     *
     * @param userId The ID of the user whose orders are to be fetched.
     * @return A [Result] containing a list of [Order] if successful, or an [Exception] on failure.
     */
    suspend fun getUserOrders(userId: String): Result<List<Order>> {
        return try {
            val querySnapshot = ordersRef.whereEqualTo("userId", userId)
                .orderBy("orderDate", Query.Direction.DESCENDING)
                .get()
                .await()
            val orders = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Order::class.java)
            }
            Result.success(orders) // Successfully fetched orders
        } catch (e: Exception) {
            Result.failure(e) // Handle exceptions during the query
        }
    }

    /**
     * Provides a [Query] for dynamic interaction (e.g., pagination) of a user's orders.
     *
     * @param userId The ID of the user whose orders are to be queried.
     * @return A [Query] object for Firestore for flexible order fetching.
     */
    fun getUserOrdersQuery(userId: String): Query {
        return ordersRef
            .whereEqualTo("userId", userId)
            .orderBy("orderDate", Query.Direction.DESCENDING) // Sort orders by most recent
    }

    suspend fun getOrderById(orderId: String): Result<com.example.mwakdasicollection.model.Order> {
        return try {
            val documentSnapshot = ordersRef.document(orderId).get().await()
            val order = documentSnapshot.toObject(com.example.mwakdasicollection.model.Order::class.java)
            if (order != null) {
                Result.success(order)
            } else {
                Result.failure(Exception("Order not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}