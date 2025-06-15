package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.repositories.AuthRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: AuthRepository) : ViewModel() {
    fun signup(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            // Handle success/failure
        }
    }
}