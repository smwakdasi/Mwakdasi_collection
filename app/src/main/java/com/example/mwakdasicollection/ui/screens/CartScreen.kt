package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mwakdasicollection.model.Cart
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CartScreen(
    navController: NavHostController,
    firestore: FirebaseFirestore,              // Missing comma is fixed here
    cartItems: List<Cart>,                     // List of cart items
    onQuantityChange: (Cart, Int) -> Unit,     // Callback for quantity change
    onRemoveItem: (Cart) -> Unit               // Callback for removing items
) {
    // Calculate the total cost of all cart items
    val totalCost = remember(cartItems) {
        cartItems.sumOf { it.price * it.quantity }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Your Cart") }
            )
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            // Show a message if the cart is empty
            EmptyCartMessage(paddingValues = paddingValues)
        } else {
            // Show the list of cart items and checkout button
            CartContent(
                cartItems = cartItems,
                totalCost = totalCost,
                paddingValues = paddingValues,
                onQuantityChange = onQuantityChange,
                onRemoveItem = onRemoveItem,
                navController = navController
            )
        }
    }
}

@Composable
private fun EmptyCartMessage(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your cart is empty. Add some items to your cart!",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CartContent(
    cartItems: List<Cart>,
    totalCost: Double,
    paddingValues: PaddingValues,
    onQuantityChange: (Cart, Int) -> Unit,
    onRemoveItem: (Cart) -> Unit,
    navController: NavHostController
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Render each cart item
        items(cartItems) { cartItem ->
            CartItem(
                cartItem = cartItem,
                onQuantityChange = onQuantityChange,
                onRemoveItem = onRemoveItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Show total cost and checkout button
        item {
            CartFooter(totalCost = totalCost, navController = navController)
        }
    }
}

@Composable
private fun CartFooter(
    totalCost: Double,
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Total cost row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total:",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$%.2f".format(totalCost), // Currency formatting
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // "Proceed to Checkout" button
        Button(
            onClick = {
                navController.navigate("checkout/${"%.2f".format(totalCost)}") // Pass totalCost to the checkout screen
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Proceed to Checkout")
        }
    }
}