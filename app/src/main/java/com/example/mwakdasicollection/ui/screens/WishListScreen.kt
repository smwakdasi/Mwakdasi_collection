package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mwakdasicollection.model.WishlistItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    navController: NavController, // Enables navigation to other screens
    wishlistItems: List<WishlistItem>, // List of wishlist items
    onWishlistItemClick: (WishlistItem) -> Unit // Callback for card click action
) {
    Scaffold(
        topBar = {
            // Top App Bar with "Wishlist" title
            TopAppBar(title = { Text("My Wishlist") })
        },
        content = { padding ->
            // Main content wrapped in padding
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (wishlistItems.isEmpty()) {
                    // Fallback UI for empty wishlist
                    EmptyWishlistView()
                } else {
                    // Displays the wishlist items in a scrolling list
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(wishlistItems) { item ->
                            WishlistCard(wishlistItem = item, onClick = { onWishlistItemClick(item) })
                        }
                    }
                }
            }
        }
    )
}

// Empty Wishlist Fallback Composable
@Composable
fun EmptyWishlistView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your Wishlist is empty!",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

// A card to display each wishlist item
@Composable
fun WishlistCard(
    wishlistItem: WishlistItem, // Data for the item
    onClick: () -> Unit // Action when the card is clicked
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }, // Clickable card to trigger action
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "User ID: ${wishlistItem.userId}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Product ID: ${wishlistItem.productId}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// Sample Data Class
data class WishlistItem(
    val userId: String,
    val productId: String
)