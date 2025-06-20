package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Inventory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class InventoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val inventoryRef = db.collection("inventory")

    /**
     * Fetches all inventory items from the Firestore `inventory` collection.
     *
     * @return A [Result] containing the list of [Inventory] objects if successful, or an [Exception] on failure.
     */
    suspend fun getInventory(): Result<List<Inventory>> {
        return try {
            val querySnapshot = inventoryRef.get().await()
            val inventoryItems = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Inventory::class.java) // Deserialize each document into an Inventory object
            }
            Result.success(inventoryItems) // Wrap the inventory list in a success Result
        } catch (e: Exception) {
            Result.failure(e) // Wrap exceptions in a failure Result
        }
    }
}