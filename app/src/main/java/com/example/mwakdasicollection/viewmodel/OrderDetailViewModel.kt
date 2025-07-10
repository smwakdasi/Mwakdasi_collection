package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.model.Order
import com.example.mwakdasicollection.repositories.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailViewModel(
    private val orderRepository: OrderRepository = OrderRepository()
) : ViewModel() {

    private val _order = MutableStateFlow<Order?>(null)
    val order: StateFlow<Order?> get() = _order

    fun loadOrder(orderId: String) {
        viewModelScope.launch {
            orderRepository.getOrderById(orderId).onSuccess { fetchedOrder ->
                _order.value = fetchedOrder
            }.onFailure {
                _order.value = null
            }
        }
    }
}