package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Inventory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryListScreen(
    inventoryItems: List<Inventory>,
    onRestockItem: (Inventory) -> Unit,
    onViewDetails: (Inventory) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inventory List") })
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(inventoryItems) { inventoryItem ->
                    InventoryItemCard(
                        inventoryItem = inventoryItem,
                        onRestock = { onRestockItem(inventoryItem) },
                        onViewDetails = { onViewDetails(inventoryItem) }
                    )
                }
            }
        }
    )
}

@Composable
fun InventoryItemCard(
    inventoryItem: Inventory,
    onRestock: () -> Unit,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = inventoryItem.productName, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Stock: ${inventoryItem.quantityAvailable}",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onViewDetails) {
                    Text("Details")
                }
                if (inventoryItem.needsRestocking()) {
                    TextButton(onClick = onRestock) {
                        Text("Restock")
                    }
                }
            }
        }
    }
}