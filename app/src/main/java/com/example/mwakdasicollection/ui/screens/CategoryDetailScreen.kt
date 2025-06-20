package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mwakdasicollection.model.Category

@Composable
fun CategoryDetailScreen(
    category: Category,
    onEditCategory: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Edit, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Image
                Image(
                    painter = rememberAsyncImagePainter(category.imageUrl),
                    contentDescription = category.name,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )

                // Details
                Text(text = "Name: ${category.name}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description: ${category.description}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                // Edit button
                Button(onClick = onEditCategory) {
                    Text("Edit Category")
                }
            }
        }
    )
}