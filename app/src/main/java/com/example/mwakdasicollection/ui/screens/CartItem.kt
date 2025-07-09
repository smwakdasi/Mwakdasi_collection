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
import com.example.mwakdasicollection.R

/**
 * Displays a single cart item with product details, quantity controls, and a remove button.
 *
 * @param cartItem        Data of the cart item to display, including product name, price, quantity, and image URL.
 * @param onQuantityChange A callback invoked when the item's quantity changes (increase or decrease).
 * @param onRemoveItem     A callback invoked when the user removes the item from the cart.
 */
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = cartItem.imageUrl,
                    error = painterResource(id = R.drawable.placeholder_image) // Fallback to a default placeholder image
                ),
                contentDescription = cartItem.productName,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )

            // Product Info and Quantity Controls
            Column(modifier = Modifier.weight(1f)) {
                // Product Name
                Text(
                    text = cartItem.productName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                // Product Price
                Text(
                    text = "Price: \$${String.format("%.2f", cartItem.price)}", // Format price to 2 decimal places
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quantity Controls
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            if (cartItem.quantity > 1) onQuantityChange(cartItem, cartItem.quantity - 1)
                        },
                        modifier = Modifier.size(32.dp) // Adjust size for compact design
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease Quantity")
                    }

                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .widthIn(min = 24.dp), // Ensure proper width for the text
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = { onQuantityChange(cartItem, cartItem.quantity + 1) },
                        modifier = Modifier.size(32.dp) // Adjust size for compact design
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                    }
                }
            }

            // Remove Button
            IconButton(
                onClick = { onRemoveItem(cartItem) },
                modifier = Modifier.size(32.dp) // Compact remove button
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Item",
                    tint = MaterialTheme.colorScheme.error // Use error color for delete action
                )
            }
        }
    }
}