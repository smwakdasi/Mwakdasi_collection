package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Transaction
import com.google.firebase.firestore.FirebaseFirestore

class TransactionRepository {
    private val db = FirebaseFirestore.getInstance()
    private val transactionsRef = db.collection("transactions")

    // Fetch transactions for a specific user
    fun getUserTransactions(userId: String, onComplete: (List<Transaction>?, Exception?) -> Unit) {
        transactionsRef.whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val transactions = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Transaction::class.java)
                }
                onComplete(transactions, null) // Return the list of transactions
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Handle errors
            }
    }
}