package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Cart
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class CartRepository {
    private val db = FirebaseFirestore.getInstance()
    private val cartsRef = db.collection("carts")

    // Fetches the user's cart document from Firestore
    fun getUserCart(userId: String, onComplete: (Cart?, Exception?) -> Unit) {
        cartsRef.document(userId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val cart = documentSnapshot.toObject<Cart>() // Deserialize the document into a Cart object
                    onComplete(cart, null) // Call onComplete with cart as the result
                } else {
                    onComplete(null, null) // No document found
                }
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception) // Return exception if there is an error
            }
    }
}