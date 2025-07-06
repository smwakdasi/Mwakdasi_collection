package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mwakdasicollection.model.Profile
import com.example.mwakdasicollection.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    // Backing property for profile data
    private val _profileData = MutableLiveData<Profile?>()
    val profileData: LiveData<Profile?> get() = _profileData

    // Backing property to manage loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Backing property to handle error messages
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    init {
        fetchProfile()
    }

    /**
     * Fetch profile data from the repository.
     * Attempts both local data retrieval and backend fetching asynchronously.
     */
    fun fetchProfile() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val profile = withContext(Dispatchers.IO) {
                    ProfileRepository.fetchProfileFromBackend()
                }
                _profileData.postValue(profile)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch profile: ${e.localizedMessage}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    /**
     * Update profile data in both the local repository and optionally backend.
     */
    fun updateProfile(newProfile: Profile) {
        try {
            // Save profile using the repository (includes validation)
            ProfileRepository.saveProfile(newProfile)
            _profileData.value = newProfile
            _errorMessage.value = null // Clear error state on successful update
        } catch (e: IllegalArgumentException) {
            // Handle validation errors during save
            _errorMessage.value = e.message
        } catch (e: Exception) {
            _errorMessage.value = "Failed to update profile: ${e.localizedMessage}"
        }
    }

    /**
     * Clear error messages to reset the UI error state.
     */
    fun clearErrors() {
        _errorMessage.value = null
    }
}