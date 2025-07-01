package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    // One-time fetch with Result handling
    suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            val querySnapshot = productsRef.get().await()
            val products = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java)
            }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Flow version for continuous observation
    fun getAllProductsAsFlow(): Flow<List<Product>> = flow {
        try {
            val querySnapshot = productsRef.get().await()
            val products = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java)
            }
            emit(products)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Fetch product by ID
    suspend fun getProductById(productId: String): Product? {
        return try {
            val documentSnapshot = productsRef.document(productId).get().await()
            documentSnapshot.toObject(Product::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
