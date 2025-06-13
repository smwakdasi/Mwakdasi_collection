package com.example.mwakdasicollection.model

data class Inventory(
    val productId: String = "",
    val productName: String = "", // Added product name for better identification
    val quantityAvailable: Int = 0,
    val restockThreshold: Int = 10 // Threshold to indicate when restocking is needed
) {

    // Checks if the item is in stock
    fun isInStock(): Boolean {
        return quantityAvailable > 0
    }

    // Checks if restocking is needed based on the given threshold
    fun needsRestocking(): Boolean {
        return quantityAvailable <= restockThreshold
    }
}