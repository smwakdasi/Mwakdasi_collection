package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Product

@Composable
fun ProductItem(
    product: Product,
    onItemClicked: (Product) -> Unit // Callback when a product is clicked
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClicked(product) }, // Handle click event
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product name
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f), // Take up the available width
                color = MaterialTheme.colorScheme.onSurface
            )

            // Product price
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )
        }
    }
}