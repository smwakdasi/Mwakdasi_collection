package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TransactionRepository {
    private val db = FirebaseFirestore.getInstance()
    private val transactionsRef = db.collection("transactions")

    /**
     * Fetches all transactions for a specific user.
     *
     * @param userId The ID of the user whose transactions are to be fetched.
     * @return A [Result] containing the list of [Transaction] objects if successful, or an [Exception] on failure.
     */
    suspend fun getUserTransactions(userId: String): Result<List<Transaction>> {
        return try {
            val querySnapshot = transactionsRef.whereEqualTo("userId", userId)
                .get()
                .await()

            val transactions = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Transaction::class.java)
            }

            Result.success(transactions) // Successfully fetched transactions
        } catch (e: Exception) {
            Result.failure(e) // Handle exceptions and return failure
        }
    }
}