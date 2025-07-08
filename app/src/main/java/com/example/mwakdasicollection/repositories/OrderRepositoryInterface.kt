package com.example.mwakdasicollection.repositories.interfaces

import com.example.mwakdasicollection.model.Order
import com.google.firebase.firestore.Query

interface OrderRepositoryInterface {
    suspend fun placeOrder(order: Order): Result<Boolean>
    suspend fun getUserOrders(userId: String): Result<List<Order>>
    fun getUserOrdersQuery(userId: String): Query
}
