package com.example.mwakdasicollection.model

data class Admin(
    val adminId: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "", // Added phone number
    val role: String = "Administrator", // Added a default role
    val isActive: Boolean = true // Added active status
) {

    // Returns a display-friendly string for the Admin
    fun getAdminInfo(): String {
        return "Admin: $name, Email: $email, Role: $role"
    }

    // Validates if the Admin's details are complete
    fun isValidAdmin(): Boolean {
        return name.isNotBlank() && email.isNotBlank() && email.contains("@")
    }
