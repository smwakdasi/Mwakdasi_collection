package com.example.mwakdasicollection.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.manager.CartManager
import com.example.mwakdasicollection.model.Order
import com.example.mwakdasicollection.repositories.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CheckoutViewModel(
    private val orderRepository: OrderRepository = OrderRepository()
) : ViewModel() {

    private val _orderStatus = MutableStateFlow<OrderStatus?>(null)
    val orderStatus: StateFlow<OrderStatus?> get() = _orderStatus

    fun placeOrder(userId: String) {
        viewModelScope.launch {
            val orderId = UUID.randomUUID().toString()
            val totalCost = CartManager.getTotalCost()
            val order = Order(
                orderId = orderId,
                userId = userId,
                items = CartManager.cartItems.toList(), // Assuming Order contains a list of Cart items
                totalAmount = totalCost,
                status = "Pending",
                orderDate = System.currentTimeMillis()

            )

            val result = orderRepository.placeOrder(order)
            if (result.isSuccess) {
                Log.d("Checkout", "Order created successfully with orderId: $orderId")
                _orderStatus.value = OrderStatus.Success
                CartManager.cartItems.clear() // Clear cart after successful order placement
            } else {
                _orderStatus.value = OrderStatus.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}

sealed class OrderStatus {
    object Success : OrderStatus()
    data class Error(val message: String) : OrderStatus()
}