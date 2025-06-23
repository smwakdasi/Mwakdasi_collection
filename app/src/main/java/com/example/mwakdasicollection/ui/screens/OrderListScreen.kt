package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mwakdasicollection.model.Order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(
    navController: NavController, // Allows navigation to other screens
    orders: List<Order>,
    onOrderClick: (Order) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Order List") })
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(orders) { order ->
                    OrderCard(order = order, onClick = { onOrderClick(order) })
                }
            }
        }
    )
}

@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }
        ) {
            Text(text = "Order ID: ${order.orderId}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Date: ${order.getFormattedOrderDate()}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Total Quantity: ${order.getTotalQuantity()}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Total Amount: $${order.totalAmount}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${order.status}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}