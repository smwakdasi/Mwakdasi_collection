package com.example.mwakdasicollection.model

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@IgnoreExtraProperties
data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    @get:PropertyName("imageurl")
    @set:PropertyName("imageurl")
    var imageUrl: String = "",
    val description: String = "",
    @get:PropertyName("category")
    @set:PropertyName("category")
    var category: String = ""
)