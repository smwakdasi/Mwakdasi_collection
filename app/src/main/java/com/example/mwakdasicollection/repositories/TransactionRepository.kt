package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Transaction
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class TransactionRepository {
    private val transactionsRef = Firebase.firestore.collection("transactions")

    fun getUserTransactions(userId: String, onComplete: (List<Transaction>) -> Unit) {
        transactionsRef.whereEqualTo("userId", userId).get().addOnSuccessListener {
            val data = it.mapNotNull { doc -> doc.toObject(Transaction::class.java) }
            onComplete(data)
        }
    }
}
