package com.example.mwakdasicollection.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthRepositoryImpl : AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance() // Firebase Authentication instance

    // To manage the authentication state as a StateFlow
    private val _isUserAuthenticated = MutableStateFlow<Boolean>(false)
    val isUserAuthenticated: StateFlow<Boolean> get() = _isUserAuthenticated

    init {
        // Initialize authentication state by checking if user is already signed in
        _isUserAuthenticated.value = firebaseAuth.currentUser != null
    }

    /**
     * Login existing users with their email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A [Result] containing `true` on success or an [Exception] on failure.
     */
    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            _isUserAuthenticated.value = true // Update state after success
            Result.success(true)
        } catch (e: Exception) {
            _isUserAuthenticated.value = false // Reset state on failure
            Result.failure(e)
        }
    }

    /**
     * Register a new user with an email and password.
     *
     * @param email The new user's email.
     * @param password The new user's password.
     * @return A [Result] containing `true` on success or an [Exception] on failure.
     */
    override suspend fun register(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            _isUserAuthenticated.value = true // Update state after success
            Result.success(true)
        } catch (e: Exception) {
            _isUserAuthenticated.value = false // Reset state on failure
            Result.failure(e)
        }
    }

    /**
     * Logout the current user.
     *
     * This method logs out the currently signed-in user and updates the authentication state.
     */
    fun logout() {
        firebaseAuth.signOut()
        _isUserAuthenticated.value = false // Update state to reflect logout
    }

    /**
     * Get the currently authenticated user ID.
     *
     * @return The current user's UID if logged in, otherwise `null`.
     */
    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}