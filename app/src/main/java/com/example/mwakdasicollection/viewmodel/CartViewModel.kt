package com.example.mwakdasicollection.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.model.Cart
import com.example.mwakdasicollection.repositories.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository = CartRepository()
) : ViewModel() {

    private val _cart = MutableStateFlow<Cart?>(null)
    val cart: StateFlow<Cart?> get() = _cart

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadCart(userId: String) {
        viewModelScope.launch {
            val result = cartRepository.getUserCart(userId)
            result.onSuccess {
                _cart.value = it
            }.onFailure {
                _error.value = it.message
            }
        }
    }
}
