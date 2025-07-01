package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    /**
     * Fetches all products from the Firestore `products` collection.
     *
     * @return A [Result] containing a list of [Product] objects if successful, or an [Exception] on failure.
     */
    suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            val querySnapshot = productsRef.get().await()
            val products = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java) // Deserialize each document into Product object
            }
            Result.success(products) // Success - return the product list
        } catch (e: Exception) {
            Result.failure(e) // Failure - return the exception
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

    /**
     * Fetches a single product by its productId from the Firestore `products` collection.
     *
     * @param productId The ID of the product to retrieve.
     * @return A [Result] containing the [Product] if found, or an [Exception] on failure.
     */
    suspend fun getProductById(productId: String): Product? {
        return try {
            val documentSnapshot = productsRef.document(productId).get().await()
            documentSnapshot.toObject(Product::class.java)
        } catch (e: Exception) {
            null
        }
    }
}