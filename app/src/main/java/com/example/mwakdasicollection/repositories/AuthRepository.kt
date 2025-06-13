package com.example.mwakdasicollection.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun register(email: String, password: String): Result<Boolean>
}

class FirebaseAuthRepository : AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            // Perform Firebase sign-in
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<Boolean> {
        return try {
            // Perform Firebase sign-up
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}