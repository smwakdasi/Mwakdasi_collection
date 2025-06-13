package com.example.mwakdasicollection.model

import java.text.SimpleDateFormat
import java.util.*

data class User(
    val uid: String = "", // Unique identifier for the user
    val name: String = "", // User's full name
    val email: String = "", // User's email address
    val createdAt: Long = System.currentTimeMillis() // Account creation timestamp
) {

    // Returns a formatted creation date string
    fun getFormattedCreationDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(createdAt))
    }

    // Checks if the email is valid using a basic regex pattern
    fun isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Returns the user's initials from their name
    fun getInitials(): String {
        val words = name.trim().split(" ")
        return words.mapNotNull { it.firstOrNull()?.uppercaseChar().toString() }.joinToString("")
    }

    // Checks if the user has provided all required information
    fun isComplete(): Boolean {
        return uid.isNotBlank() && name.isNotBlank() && email.isNotBlank()
    }
}