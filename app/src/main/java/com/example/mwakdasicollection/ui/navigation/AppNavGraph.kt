package com.example.mwakdasicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mwakdasicollection.ui.screens.*
import com.example.mwakdasicollection.model.Cart
import com.example.mwakdasicollection.model.WishlistItem
import com.example.mwakdasicollection.model.Order
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AppNavGraph(
    isUserAuthenticated: Boolean,
    firestore: FirebaseFirestore
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (isUserAuthenticated) "home" else "login"
    ) {
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
            SignupScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                firestore = firestore,
                cartItems = loadCartItems(firestore), // Dynamically load cart items
                onQuantityChange = { cartItem, newQuantity ->
                    updateCartItemQuantity(firestore, cartItem, newQuantity) // Update quantity in Firestore
                },
                onRemoveItem = { cartItem ->
                    removeCartItem(firestore, cartItem) // Remove item from Firestore
                }
            )
        }

        composable("wishlist") {
            WishlistScreen(
                navController = navController,
                firestore = firestore,
                wishlistItems = loadWishlistItems(firestore), // Dynamically load wishlist items
                onWishlistItemClick = { wishlistItem ->
                    println("Clicked wishlist item: ${wishlistItem.name}")
                }
            )
        }

        composable("orders") {
            OrderListScreen(
                navController = navController,
                firestore = firestore,
                orders = loadOrders(firestore), // Dynamically load orders
                onOrderClick = { order ->
                    println("Clicked order: ${order.id}")
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                navController = navController,
                goToEditProfile = { navController.navigate("edit_profile") }
            )
        }

        composable("edit_profile") {
            EditProfileScreen(
                navController = navController,
                onProfileSaved = { navController.popBackStack() }
            )
        }
    }
}