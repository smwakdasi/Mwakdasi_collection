package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.model.Order
import com.example.mwakdasicollection.repositories.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderListViewModel(
    private val orderRepository: OrderRepository = OrderRepository()
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders

    fun loadOrders(userId: String) {
        viewModelScope.launch {
            orderRepository.getUserOrders(userId).onSuccess { ordersList ->
                _orders.value = ordersList
            }.onFailure {
                // In production, show an error UI
                _orders.value = emptyList()
            }
        }
    }
}