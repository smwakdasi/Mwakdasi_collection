package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Category
import com.google.firebase.firestore.FirebaseFirestore

class CategoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val categoryRef = db.collection("categories")

    // Fetches all categories
    fun getCategories(onComplete: (List<Category>?, Exception?) -> Unit) {
        categoryRef.get()
            .addOnSuccessListener { querySnapshot ->
                val categories = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Category::class.java) // Map each document into a Category object
                }
                onComplete(categories, null) // Pass the category list to the callback
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Pass the error to the callback
            }
    }
}