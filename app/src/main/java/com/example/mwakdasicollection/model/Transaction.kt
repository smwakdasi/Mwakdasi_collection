package com.example.mwakdasicollection.model

import java.text.SimpleDateFormat
import java.util.*

data class Transaction(
    val transactionId: String = "",
    val userId: String = "",
    val totalAmount: Double = 0.0, // Total monetary value of the transaction
    val timestamp: Long = System.currentTimeMillis(), // When the transaction occurred
    val status: String = "pending", // Status: pending, completed, failed, etc.
    val currency: String = "USD" // Transaction currency, e.g., USD, EUR, KES
) {

    // Converts the timestamp into a readable date-time string
    fun getFormattedTimestamp(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    // Checks if the transaction amount is valid (greater than 0)
    fun isValidAmount(): Boolean {
        return totalAmount > 0
    }

    // Provides a user-friendly summary of the transaction
    fun getTransactionSummary(): String {
        return "Transaction(ID: $transactionId, Amount: $totalAmount $currency, Status: $status, Date: ${getFormattedTimestamp()})"
    }
}