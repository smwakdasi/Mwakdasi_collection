package com.example.mwakdasicollection.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance() // Firebase Auth instance

    // Implements the login method using FirebaseAuth
    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await() // Asynchronous login
            Result.success(true) // Login successful
        } catch (e: Exception) {
            Result.failure(e) // Return failure with the exception
        }
    }

    // Implements the register method using FirebaseAuth
    override suspend fun register(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await() // Asynchronous registration
            Result.success(true) // Registration successful
        } catch (e: Exception) {
            Result.failure(e) // Return failure with the exception
        }
    }
}