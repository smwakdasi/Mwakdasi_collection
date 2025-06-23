package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mwakdasicollection.model.ProfileFragment
import com.example.mwakdasicollection.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController, // Allows navigation to other screens
    onProfileSaved: () -> Unit,  // Callback for navigation after saving
    viewModel: ProfileViewModel = viewModel()
) {
    val profile = viewModel.profileData.value

    var name by remember { mutableStateOf(profile?.name ?: "") }
    var email by remember { mutableStateOf(profile?.email ?: "") }
    var profileImageUrl by remember { mutableStateOf(profile?.profileImageUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Name Input
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Input
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Image URL Input
                OutlinedTextField(
                    value = profileImageUrl,
                    onValueChange = { profileImageUrl = it },
                    label = { Text("Profile Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = {
                        viewModel.updateProfile(
                            ProfileFragment(
                                id = profile?.id ?: "",
                                name = name,
                                email = email,
                                profileImageUrl = profileImageUrl
                            )
                        )
                        onProfileSaved() // Navigate back to ProfileScreen
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Profile")
                }
            }
        }
    )
}
