package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mwakdasicollection.model.Cart

@Composable
fun CartScreen(
    navController: NavController,
    cartItems: List<Cart>,                      // List of cart items
    onQuantityChange: (Cart, Int) -> Unit,     // Callback for quantity change
    onRemoveItem: (Cart) -> Unit               // Callback for removing items
) {
    // Calculate total cost based on cart items
    val totalCost = remember(cartItems) {
        cartItems.sumOf { it.price * it.quantity }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Cart") })
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            // Display a message if the cart is empty
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Your cart is empty. Add some items to your cart!",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Display list of cart items
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(cartItems) { cartItem ->
                    CartItem(
                        cartItem = cartItem,
                        onQuantityChange = onQuantityChange,
                        onRemoveItem = onRemoveItem
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    // Total and Proceed to Checkout Button
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // Total Cost Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total:",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "$%.2f".format(totalCost), // Two decimal places for currency
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        // Proceed to Checkout Button
                        Button(
                            onClick = {
                                navController.navigate("checkout/${"%.2f".format(totalCost)}") // Pass total cost
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Proceed to Checkout")
                        }
                    }
                }
            }
        }
    }
}