package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mwakdasicollection.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController, // Allows navigation to other screens
    goToEditProfile: () -> Unit,  // Navigation to Edit Profile Screen
    viewModel: ProfileViewModel = viewModel()
) {
    val profile = viewModel.profileData.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    IconButton(onClick = goToEditProfile) {
                        Icon(Icons.Default.Edit, "Edit Profile")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Image
                profile?.profileImageUrl?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(16.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Profile Name
                Text(
                    text = profile?.name ?: "Unknown",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                // Profile Email
                Text(
                    text = profile?.email ?: "No email available",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }
    )
}
