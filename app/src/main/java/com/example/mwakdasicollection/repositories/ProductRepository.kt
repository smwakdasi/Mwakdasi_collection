package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Product
import com.google.firebase.firestore.FirebaseFirestore
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

    /**
     * Fetches a single product by its productId from the Firestore `products` collection.
     *
     * @param productId The ID of the product to retrieve.
     * @return A [Result] containing the [Product] if found, or an [Exception] on failure.
     */
    suspend fun getProductById(productId: String): Result<Product?> {
        return try {
            val documentSnapshot = productsRef.document(productId).get().await()
            val product = documentSnapshot.toObject(Product::class.java)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}