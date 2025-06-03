package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Product
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ProductRepository {
    private val productsRef = Firebase.firestore.collection("products")

    fun getAllProducts(onComplete: (List<Product>) -> Unit) {
        productsRef.get().addOnSuccessListener { result ->
            val products = result.mapNotNull { it.toObject(Product::class.java) }
            onComplete(products)
        }
    }
}