package com.example.mwakdasicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mwakdasicollection.ui.screens.*
import com.example.mwakdasicollection.ui.screens.ProfileScreen
import com.example.mwakdasicollection.ui.screens.EditProfileScreen
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AppNavGraph(isUserAuthenticated: Boolean, firestore: FirebaseFirestore) {
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
            WishlistScreen(
                navController = navController,
                wishlistItems = TODO(),
                onWishlistItemClick = TODO()
            )
        }

        composable("orders") {
            OrderListScreen(
                navController = navController,
                orders = TODO(),
                onOrderClick = TODO()
            )
        }

        // New route for Order Creation Screen
        composable("order_creation/{userId}") { backStackEntry ->
            // Retrieve the userId from navigation arguments
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            OrderCreationScreen(
                userId = userId,
                navController = navController
            )
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

        // Product List and Details
        composable("product_list") {
            ProductListScreen(
                firestore = firestore,
                navController = navController
            )
        }

//        To enable product detail navigation, update both the navigation route and the clickable lambda accordingly.
        composable("product_item/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailWrapper(
                productId = productId,
                firestore = firestore,
                onBack = { navController.popBackStack() } // Navigate back to ProductListScreen
            )
        }
    }
}