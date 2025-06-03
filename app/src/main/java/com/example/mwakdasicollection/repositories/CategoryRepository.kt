package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Category
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CategoryRepository {
    private val categoryRef = Firebase.firestore.collection("categories")

    fun getCategories(onComplete: (List<Category>) -> Unit) {
        categoryRef.get().addOnSuccessListener {
            val categories = it.mapNotNull { doc -> doc.toObject(Category::class.java) }
            onComplete(categories)
        }
    }
}