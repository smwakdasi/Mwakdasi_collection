package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mwakdasicollection.manager.CartManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val cartItems = CartManager.cartItems

    // Using derivedStateOf ensures these values update whenever cartItems changes.
    val totalCost by remember { derivedStateOf { CartManager.getTotalCost() } }
    val totalItems by remember { derivedStateOf { CartManager.getTotalItems() } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart") }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                // Display Cart List using a LazyColumn
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(cartItems) { cartItem ->
                        // Assume you have an existing Composable to display each item
                        CartItem(
                            cartItem = cartItem,
                            onQuantityChange = { item, newQuantity ->
                                CartManager.updateQuantity(item, newQuantity)
                            },
                            onRemoveItem = { item ->
                                CartManager.removeItem(item)
                            }
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Items: $totalItems")
                    Text("Total Cost: KSH ${"%.2f".format(totalCost)}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle checkout */ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text("Proceed to Checkout")
                }
            }
        }
    )
}