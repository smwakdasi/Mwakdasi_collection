package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val categoryRef = db.collection("categories")

    /**
     * Fetches all categories stored in the Firestore `categories` collection.
     *
     * @return A [Result] containing a list of [Category] objects if successful, or an [Exception] on failure.
     */
    suspend fun getCategories(): Result<List<Category>> {
        return try {
            val querySnapshot = categoryRef.get().await()
            val categories = querySnapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(Category::class.java) // Deserialize each document into a Category object
            }
            Result.success(categories) // Wrap the category list in a success Result
        } catch (e: Exception) {
            Result.failure(e) // Wrap any exception in a failure Result
        }
    }
}