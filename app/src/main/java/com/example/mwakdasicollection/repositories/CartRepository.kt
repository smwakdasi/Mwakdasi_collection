package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Cart
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CartRepository {
    private val cartsRef = Firebase.firestore.collection("carts")

    fun getUserCart(userId: String, onComplete: (Cart?) -> Unit) {
        cartsRef.document(userId).get().addOnSuccessListener {
            onComplete(it.toObject(Cart::class.java))
        }
    }
}