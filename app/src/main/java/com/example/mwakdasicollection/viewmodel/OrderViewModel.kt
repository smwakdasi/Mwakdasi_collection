package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.model.CartItem
import com.example.mwakdasicollection.model.Order
import com.example.mwakdasicollection.repositories.OrderRepository
import com.example.mwakdasicollection.providers.CartProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import com.example.mwakdasicollection.model.Cart

// Extension function to convert Cart to CartItem
fun Cart.toCartItem(): CartItem {
    return CartItem(
        productId = this.productId,
        productName = this.productName,
        quantity = this.quantity,
        price = this.price
    )
}

class OrderViewModel(
    private val cartProvider: CartProvider  // Injected dependency for retrieving cart items
) : ViewModel() {

    private val orderRepository = OrderRepository()

    // StateFlow to hold the orders list for UI observation
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    /**
     * Creates an order by fetching cart items via the CartProvider.
     */
    fun createOrder(userId: String) {
        viewModelScope.launch {
            // Retrieve cart items from the abstraction
            val cartItems = cartProvider.getCartItems()
            // Convert Cart to CartItem for order
            val orderCartItems = cartItems.map { it.toCartItem() }
            // Create the order (orderId generated via UUID)
            val order = Order(
                orderId = UUID.randomUUID().toString(),
                userId = userId,
                items = orderCartItems
            )
            val result = orderRepository.placeOrder(order)
            result.onSuccess { success ->
                if (success) {
                    // Optionally notify the UI or log success
                    fetchUserOrders(userId)
                }
            }.onFailure { exception ->
                // Handle any errors (e.g., show a toast or log)
                exception.printStackTrace()
            }
        }
    }

    /**
     * Fetches all orders for a specified user.
     */
    fun fetchUserOrders(userId: String) {
        viewModelScope.launch {
            val result = orderRepository.getUserOrders(userId)
            result.onSuccess { orderList ->
                _orders.value = orderList
            }.onFailure { exception ->
                exception.printStackTrace()
            }
        }
    }
}