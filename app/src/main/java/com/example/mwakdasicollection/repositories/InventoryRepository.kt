package com.example.mwakdasicollection.repositories

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class InventoryRepository {
    private val inventoryRef = Firebase.firestore.collection("inventory")

    fun getInventory(onResult: (List<InventoryItem>) -> Unit) {
        inventoryRef.get().addOnSuccessListener {
            onResult(it.mapNotNull { doc -> doc.toObject(InventoryItem::class.java) })
        }
    }
}

annotation class InventoryItem
