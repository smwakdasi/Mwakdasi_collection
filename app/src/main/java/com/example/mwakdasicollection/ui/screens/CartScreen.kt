package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

// Data model for the cart item
data class Cart(
    val productId: String,      // Unique product ID
    val productName: String,    // Name of the product
    val quantity: Int,          // Quantity selected
    val price: Double,          // Price of a single item
    val imageUrl: String        // Product image URL
) {
    // Convenience function: calculates the total cost for this item
    fun getTotalCost(): Double = quantity * price
}

// Main screen composable
@Composable
fun CartScreen() {
    // Manage the state of cart items
    var cartItems by remember {
        mutableStateOf(
            listOf(
                Cart("1", "Product A", 2, 50.0, "https://via.placeholder.com/80"),
                Cart("2", "Product B", 1, 20.5, "https://via.placeholder.com/80"),
                Cart("3", "Product C", 3, 15.0, "https://via.placeholder.com/80")
            )
        )
    }

    // Callback to update the quantity of an item
    fun updateQuantity(cartItem: Cart, newQuantity: Int) {
        cartItems = cartItems.map {
            if (it.productId == cartItem.productId) it.copy(quantity = newQuantity) else it
        }
    }

    // Callback to remove an item from the cart
    fun removeItem(cartItem: Cart) {
        cartItems = cartItems.filter { it.productId != cartItem.productId }
    }

    // Calculate the total cost and total quantity
    val totalCost by derivedStateOf {
        cartItems.sumOf { it.getTotalCost() }
    }
    val totalItems by derivedStateOf {
        cartItems.sumOf { it.quantity }
    }

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
                // Cart List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems) { cartItem ->
                        CartItem(
                            cartItem = cartItem,
                            onQuantityChange = { item, newQuantity -> updateQuantity(item, newQuantity) },
                            onRemoveItem = { item -> removeItem(item) }
                        )
                    }
                }

                // Cart Summary
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Total Items: $totalItems",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Total Cost: \$${"%.2f".format(totalCost)}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle checkout logic here */ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text("Proceed to Checkout")
                }
            }
        }
    )
}

// Composable for individual cart items
@Composable
fun CartItem(
    cartItem: Cart,
    onQuantityChange: (Cart, Int) -> Unit,
    onRemoveItem: (Cart) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(cartItem.imageUrl),
                contentDescription = cartItem.productName,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.productName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Price: \$${cartItem.price}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quantity controls
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Decrease quantity button
                    IconButton(onClick = {
                        if (cartItem.quantity > 1) {
                            onQuantityChange(cartItem, cartItem.quantity - 1)
                        }
                    }) {
                        Text("-")
                    }

                    // Quantity display
                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    // Increase quantity button
                    IconButton(onClick = { onQuantityChange(cartItem, cartItem.quantity + 1) }) {
                        Text("+")
                    }
                }
            }

            // Remove item button
            IconButton(
                onClick = { onRemoveItem(cartItem) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove item"
                )
            }
        }
    }
}