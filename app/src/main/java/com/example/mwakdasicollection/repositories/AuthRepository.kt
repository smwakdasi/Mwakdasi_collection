package com.example.mwakdasicollection.repositories

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun register(email: String, password: String): Result<Boolean>
    fun logout(): Result<Boolean>
    fun getCurrentUserId(): String?
    suspend fun resetPassword(email: String): Result<Boolean>
}