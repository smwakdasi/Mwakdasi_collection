package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mwakdasicollection.repositories.AuthRepositoryImpl

class AuthViewModelFactory(
    private val repository: AuthRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}