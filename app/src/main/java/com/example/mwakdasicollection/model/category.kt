package com.example.mwakdasicollection.model

data class Category(
    val id: String = "",
    val name: String = "",
    val description: String = "", // Added description for more details
    val imageUrl: String = "" // Added image URL for category visualization
) {

    // Checks if the category is valid
    fun isValidCategory(): Boolean {
        return id.isNotBlank() && name.isNotBlank()
    }

    // Provides a summary of the category
    fun getCategorySummary(): String {
        return "Category: $name - $description"
    }
}