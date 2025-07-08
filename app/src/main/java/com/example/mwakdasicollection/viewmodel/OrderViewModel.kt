package com.example.mwakdasicollection.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.model.Order
import com.example.mwakdasicollection.repositories.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepository: OrderRepository = OrderRepository()
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders

    private val _orderResult = MutableStateFlow<Result<Boolean>?>(null)
    val orderResult: StateFlow<Result<Boolean>?> get() = _orderResult

    fun fetchOrders(userId: String) {
        viewModelScope.launch {
            val result = orderRepository.getUserOrders(userId)
            result.onSuccess {
                _orders.value = it
            }.onFailure {
                _orders.value = emptyList() // Or show error state
            }
        }
    }

    fun placeOrder(order: Order) {
        viewModelScope.launch {
            val result = orderRepository.placeOrder(order)
            _orderResult.value = result
        }
    }
}
