package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mwakdasicollection.viewmodel.OrderViewModel
import com.example.mwakdasicollection.viewmodel.OrderViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCreationScreen(
    userId: String,
    navController: NavController
) {
    // Create the OrderViewModel using our custom factory that injects DummyCartProvider.
    val orderViewModel: OrderViewModel = viewModel(factory = OrderViewModelFactory())
    val orders by orderViewModel.orders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Create Order") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { orderViewModel.createOrder(userId) }
                ) {
                    Text("Place Order")
                }
                Spacer(modifier = Modifier.height(24.dp))
                if (orders.isNotEmpty()) {
                    Text(
                        text = "Existing Orders:",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    orders.forEach { order ->
                        Text("Order ID: ${order.orderId}, Total: ${order.totalAmount}")
                    }
                }
            }
        }
    )
}