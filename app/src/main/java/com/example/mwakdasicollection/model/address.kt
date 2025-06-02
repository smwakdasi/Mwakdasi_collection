package com.example.mwakdasicollection.model

data class Address(
    val addressId: String = "",
    val userId: String = "",
    val street: String = "",
    val city: String = "",
    val zipCode: String = "",
    val isDefault: Boolean = false
)
