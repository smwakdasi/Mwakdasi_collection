package com.example.mwakdasicollection.model

data class ProfileFragment(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.isNotBlank()) { "Email cannot be blank" }
        require(isEmailValid(email)) { "Invalid email address" }
    }

    companion object {
        private fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}