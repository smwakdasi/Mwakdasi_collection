package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mwakdasicollection.model.Cart

@Composable
fun CartItem(
    cartItem: Cart,
    onQuantityChange: (Cart, Int) -> Unit, // Callback for changing quantity
    onRemoveItem: (Cart) -> Unit          // Callback for removing item
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // Fix: Added proper alignment import
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = cartItem.imageUrl,
                    error = painterResource(id = R.drawable.placeholder_image) // Fix: Ensure placeholder_image exists
                ),
                contentDescription = cartItem.productName,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )

            // Product Info with Quantity Controls
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.productName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Price: \$${cartItem.price}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Quantity Controls with Icons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            if (cartItem.quantity > 1) onQuantityChange(cartItem, cartItem.quantity - 1)
                        }
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease Quantity")
                    }

                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    IconButton(onClick = { onQuantityChange(cartItem, cartItem.quantity + 1) }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                    }
                }
            }

            // Remove Button
            IconButton(onClick = { onRemoveItem(cartItem) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Item"
                )
            }
        }
    }
}