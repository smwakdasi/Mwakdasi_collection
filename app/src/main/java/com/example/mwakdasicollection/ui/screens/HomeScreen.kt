package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController // Allows navigation to other screens
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation drawer or another action */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Welcome Text
                Text(
                    text = "Welcome to Home Screen!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )

                // Navigation buttons to other screens
                Button(
                    onClick = {
                        // Navigate to CartScreen
                        navController.navigate("cart")
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Go to Cart")
                }

                Button(
                    onClick = {
                        // Navigate to WishlistScreen
                        navController.navigate("wishlist")
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Go to Wishlist")
                }

                Button(
                    onClick = {
                        // Navigate to OrdersScreen
                        navController.navigate("orders")
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Go to Orders")
                }

                Button(
                    onClick = {
                        // Navigate to ProfileScreen
                        navController.navigate("profile")
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Go to Profile")
                }
            }
        }
    )
}