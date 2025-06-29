package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.repositories.AuthRepositoryImpl
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepositoryImpl) : ViewModel() {

    // Expose authentication state as a StateFlow
    val isUserAuthenticated: StateFlow<Boolean> get() = authRepository.isUserAuthenticated

    /**
     * Performs login action using email and password and updates authentication state
     *
     * @param email The user's email.
     * @param password The user's password.
     * @param onSuccess Optional success callback.
     * @param onError Optional error callback with exception.
     */
    fun login(
        email: String,
        password: String,
        onSuccess: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.fold(
                onSuccess = { onSuccess?.invoke() },
                onFailure = { onError?.invoke(it) }
            )
        }
    }

    /**
     * Registers a new user with the provided email and password.
     *
     * @param email The new user's email.
     * @param password The new user's password.
     * @param onSuccess Optional success callback.
     * @param onError Optional error callback with exception.
     */
    fun signup(
        email: String,
        password: String,
        onSuccess: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            val result = authRepository.register(email, password)
            result.fold(
                onSuccess = { onSuccess?.invoke() },
                onFailure = { onError?.invoke(it) }
            )
        }
    }

    /**
     * Logs out the current user.
     */
    fun logout() {
        authRepository.logout()
    }

    /**
     * Get the current user ID if logged in.
     *
     * @return The current user's UID or `null` if not logged in.
     */
    fun getCurrentUserId(): String? {
        return authRepository.getCurrentUserId()
    }
}