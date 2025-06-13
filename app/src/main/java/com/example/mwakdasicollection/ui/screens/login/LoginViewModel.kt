package com.example.mwakdasicollection.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.repositories.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            // Handle success/failure
        }
    }
}
