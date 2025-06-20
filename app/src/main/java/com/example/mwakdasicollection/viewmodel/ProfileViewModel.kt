package com.example.mwakdasicollection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mwakdasicollection.model.ProfileFragment
import com.example.mwakdasicollection.repositories.ProfileRepository

class ProfileViewModel : ViewModel() {

    // Backing property for profile data
    private val _profileData = MutableLiveData<ProfileFragment>()
    val profileData: LiveData<ProfileFragment> get() = _profileData

    init {
        // Fetch data from the repository
        _profileData.value = ProfileRepository.fetchProfile()
    }

    // Update profile
    fun updateProfile(newProfile: ProfileFragment) {
        _profileData.value = newProfile
        ProfileRepository.saveProfile(newProfile) // Save using repository
    }
}