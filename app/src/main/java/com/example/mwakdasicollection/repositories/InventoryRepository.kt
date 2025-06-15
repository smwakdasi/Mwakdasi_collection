package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Inventory
import com.google.firebase.firestore.FirebaseFirestore

class InventoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val inventoryRef = db.collection("inventory")

    // Fetches inventory items from Firestore
    fun getInventory(onResult: (List<Inventory>?, Exception?) -> Unit) {
        inventoryRef.get()
            .addOnSuccessListener { querySnapshot ->
                val inventory = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Inventory::class.java) // Deserialize Firestore documents into InventoryItem objects
                }
                onResult(inventory, null) // Return the inventory list
            }
            .addOnFailureListener { exception ->
                onResult(null, exception) // Handle errors
            }
    }
}