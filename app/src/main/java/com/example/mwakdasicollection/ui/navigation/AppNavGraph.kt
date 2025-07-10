package com.example.mwakdasicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mwakdasicollection.ui.screens.*
import com.example.mwakdasicollection.ui.screens.ProfileScreen
import com.example.mwakdasicollection.ui.screens.EditProfileScreen
import com.example.mwakdasicollection.ui.screens.OrderDetailScreen
import com.example.mwakdasicollection.viewmodel.AuthViewModel
import com.example.mwakdasicollection.viewmodel.OrderDetailViewModel
import com.example.mwakdasicollection.viewmodel.OrderListViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AppNavGraph(
    isUserAuthenticated: Boolean,
    firestore: FirebaseFirestore,
    authViewModel: AuthViewModel
) {
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
            SignupScreen(navController = navController)
        }

        // Home Flow
        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("cart") {
            CartScreen(navController = navController)
        }

        // Product List and Details
        composable("product_list") {
            ProductListScreen(
                firestore = firestore,
                navController = navController
            )
        }
        composable("product_item/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailWrapper(
                productId = productId,
                firestore = firestore,
                onBack = { navController.popBackStack() }
            )
        }

        // Checkout route uses viewModel() to obtain AuthViewModel in order to get the current user ID.
        composable("checkout") {
            val userId = authViewModel.getCurrentUserId() ?: error("User not logged in")
            CheckoutScreen(
                userId = userId,
                onOrderSuccess = {
                    navController.navigate("orders") {
                        popUpTo("checkout") { inclusive = true }
                    }
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable("wishlist") {
            WishlistScreen(
                navController = navController,
                wishlistItems = emptyList(),
                onWishlistItemClick = { /* Implement as needed */ }
            )
        }

        composable("orders") {
            val userId = authViewModel.getCurrentUserId() ?: error("User not logged in")
            val orderListViewModel: OrderListViewModel = viewModel()
            LaunchedEffect(userId) {
                orderListViewModel.loadOrders(userId)
            }
            val orders = orderListViewModel.orders.collectAsState().value

            OrderListScreen(
                navController = navController,
                orders = orders,
                onOrderClick = { order ->
                    navController.navigate("order_details/${order.orderId}")
                }
            )
        }

        // Order Details Screen using OrderDetailViewModel.
        composable("order_details/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: error("Missing orderId")
            val orderDetailViewModel: OrderDetailViewModel = viewModel()
            LaunchedEffect(orderId) {
                orderDetailViewModel.loadOrder(orderId)
            }
            val order = orderDetailViewModel.order.collectAsState().value

            if (order == null) {
                navController.navigate("orders") {
                    popUpTo("orders") { inclusive = true }
                }
            } else {
                OrderDetailScreen(
                    order = order,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // Profile Flow
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