package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.CartItem
import com.example.mwakdasicollection.model.Order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    order: Order,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details: ${order.orderId}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Order ID: ${order.orderId}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Date: ${order.getFormattedOrderDate()}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Status: ${order.status}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(order.items) { item ->
                        CartItemView(item)
                    }
                }
            }
        }
    )
}

@Composable
fun CartItemView(item: CartItem) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Product: ${item.productName}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Price per unit: $${item.price}", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = "Total: $${item.quantity * item.price}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}