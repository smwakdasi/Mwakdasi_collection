package com.example.mwakdasicollection.model

data class Address(
    val addressId: String = "",
    val userId: String = "",
    val street: String = "",
    val city: String = "",
    val state: String = "", // Added state for completeness
    val zipCode: String = "",
    val country: String = "Kenya", // Added a default country
    val isDefault: Boolean = false
) {

    // Helper function to display the full address as a single string
    fun getFullAddress(): String {
        return "$street, $city, $state, $zipCode, $country"
    }

    // Indicates if the address is complete
    fun isAddressValid(): Boolean {
        return street.isNotBlank() && city.isNotBlank() && zipCode.isNotBlank()
    }
}