package com.example.mwakdasicollection.model

data class Profile(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null // Nullable in case the user doesn't upload an image
)