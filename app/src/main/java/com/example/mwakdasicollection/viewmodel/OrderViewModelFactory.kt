package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mwakdasicollection.providers.DummyCartProvider

class OrderViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            // Inject the dummy implementation while testing order creation logic
            return OrderViewModel(cartProvider = DummyCartProvider()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}