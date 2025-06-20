package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.ProfileFragment

object ProfileRepository {

    // To represent stored or fetched profile (use actual database or API integration)
    private var storedProfile: ProfileFragment? = null

    // Fetch profile (simulate an API call or database retrieval, for example)
    fun fetchProfile(): ProfileFragment {
        return storedProfile ?: ProfileFragment(
            id = "12345",
            name = "John Doe",
            email = "john.doe@example.com",
            profileImageUrl = "https://example.com/john-doe-profile.jpg"
        )
    }

    // Save or update the profile (e.g., update in database or send to API)
    fun saveProfile(profile: ProfileFragment) {
        storedProfile = profile
        // Logic to persist this to the backend or local database
    }
}