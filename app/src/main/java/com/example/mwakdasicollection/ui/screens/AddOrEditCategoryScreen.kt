package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditCategoryScreen(
    category: Category?,
    onSaveCategory: (Category) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(category?.name.orEmpty()) }
    var description by remember { mutableStateOf(category?.description.orEmpty()) }
    var imageUrl by remember { mutableStateOf(category?.imageUrl.orEmpty()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (category == null) "Add Category" else "Edit Category") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = onCancel) {
                        Text("Cancel")
                    }

                    Button(onClick = {
                        val newCategory = Category(
                            id = category?.id ?: System.currentTimeMillis().toString(),
                            name = name,
                            description = description,
                            imageUrl = imageUrl
                        )
                        onSaveCategory(newCategory)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    )
}