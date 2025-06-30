package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.ProfileFragment

object ProfileRepository {

    // Locally stored or fetched profile
    @Volatile
    private var storedProfile: ProfileFragment? = null

    /**
     * Fetch the current profile.
     * Returns the stored profile if available or fetches a placeholder profile.
     */
    fun fetchProfile(): ProfileFragment {
        return storedProfile ?: fetchDefaultProfile()
    }

    /**
     * Save or update the profile.
     * Adds validation for required fields. Optionally persists data to the backend.
     */
    fun saveProfile(profile: ProfileFragment) {
        // Validate required fields
        validateProfile(profile)

        // Update the locally stored profile
        storedProfile = profile

        // Persist the profile to the backend or database (if applicable)
        persistProfileToBackend(profile)
    }

    /**
     * Retrieve the current profile (null if not yet fetched or saved locally).
     */
    fun getCurrentProfile(): ProfileFragment? {
        return storedProfile
    }

    /**
     * Simulates fetching a profile from the backend.
     * Replace with actual backend or database logic for dynamic profile retrieval.
     */
    suspend fun fetchProfileFromBackend(): ProfileFragment {
        // Simulate a backend/database fetch
        return storedProfile ?: fetchDefaultProfile().also { storedProfile = it }
    }

    /**
     * Simulates saving a profile to the backend.
     * Replace with actual backend or database logic for dynamic profile persistence.
     */
    private fun persistProfileToBackend(profile: ProfileFragment) {
        // Simulate saving profile to the database or API
        println("Persisting profile to backend: $profile")
    }

    /**
     * Fetch a default or guest profile.
     */
    private fun fetchDefaultProfile(): ProfileFragment {
        return ProfileFragment(
            id = "default_id",
            name = "Guest User",
            email = "guest@example.com",
            profileImageUrl = "https://example.com/guest-profile.jpg"
        )
    }

    /**
     * Validate the profile fields.
     * Throws an exception if validation fails.
     */
    private fun validateProfile(profile: ProfileFragment) {
        require(profile.name.isNotBlank()) { "Name cannot be blank" }
        require(profile.email.isNotBlank()) { "Email cannot be blank" }
        require(isEmailValid(profile.email)) { "Invalid email address format" }
    }

    /**
     * Utility method to validate email format.
     */
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}