package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Inventory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryDetailScreen(
    inventory: Inventory,
    onRestock: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(inventory.productName) },
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = inventory.productName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Available Quantity: ${inventory.quantityAvailable}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = if (inventory.needsRestocking()) "Restocking Needed!" else "Sufficient Stock",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (inventory.needsRestocking()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (inventory.needsRestocking()) {
                    Button(onClick = onRestock) {
                        Text("Restock Now")
                    }
                }
            }
        }
    )
}