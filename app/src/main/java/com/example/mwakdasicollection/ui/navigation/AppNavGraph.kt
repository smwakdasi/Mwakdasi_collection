package com.example.mwakdasicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mwakdasicollection.ui.screens.*
import com.example.mwakdasicollection.ui.screens.ProfileScreen
import com.example.mwakdasicollection.ui.screens.EditProfileScreen

@Composable
fun AppNavGraph(isUserAuthenticated: Boolean) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (isUserAuthenticated) "home" else "login"
    ) {
        // Authentication Flow (Login & Signup)
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("signup") {
            SignupScreen(
                navController = navController
            )
        }

        // Home Flow (HomeScreen with navigable routes)
        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("cart") {
            CartScreen(navController = navController) // Create this screen
        }

        composable("wishlist") {
            WishlistScreen(navController = navController) // Create this screen
        }

        composable("orders") {
            OrderListScreen(navController = navController) // Create this screen
        }

        // Profile Flow (Profile and Edit Profile Screens)
        composable("profile") {
            ProfileScreen(
                navController = navController,
                goToEditProfile = { navController.navigate("edit_profile") }
            )
        }

        composable("edit_profile") {
            EditProfileScreen(
                navController = navController,
                onProfileSaved = { navController.popBackStack() } // Navigate back to ProfileScreen
            )
        }
    }
}