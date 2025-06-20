package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.WishlistItem

@Composable
fun WishlistScreen(
    wishlistItems: List<WishlistItem>,
    onWishlistItemClick: (WishlistItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Wishlist") })
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(wishlistItems) { item ->
                    WishlistCard(wishlistItem = item, onClick = { onWishlistItemClick(item) })
                }
            }
        }
    )
}

@Composable
fun WishlistCard(
    wishlistItem: WishlistItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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