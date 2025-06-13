package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    // Fetch all products
    fun getAllProducts(onComplete: (List<Product>?, Exception?) -> Unit) {
        productsRef.get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull { document ->
                    document.toObject(Product::class.java)
                }
                onComplete(products, null) // Return the list of products
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Return the exception in case of failure
            }
    }
}