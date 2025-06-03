package com.example.mwakdasicollection.model

data class Transaction(
    val transactionId: String = "",
    val userId: String = "",
    val totalAmount: Double = 0.0,
    val timestamp: Long = 0L
)
