package com.example.mwakdasicollection.ui.screens

import androidx.compose.runtime.*
import com.example.mwakdasicollection.model.Category

@Composable
fun CategoryManagementApp() {
    var isAddingOrEditing by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var categories by remember {
        mutableStateOf(
            listOf(
                Category("1", "Electronics", "Devices and Gadgets", "https://via.placeholder.com/150"),
                Category("2", "Clothing", "Apparel and Accessories", "https://via.placeholder.com/150")
            )
        )
    }

    when {
        isAddingOrEditing -> {
            AddOrEditCategoryScreen(
                category = selectedCategory,
                onSaveCategory = { category ->
                    if (categories.any { it.id == category.id }) {
                        categories = categories.map { if (it.id == category.id) category else it }
                    } else {
                        categories = categories + category
                    }
                    isAddingOrEditing = false
                },
                onCancel = { isAddingOrEditing = false }
            )
        }
        selectedCategory != null -> {
            CategoryDetailScreen(
                category = selectedCategory!!,
                onEditCategory = {
                    isAddingOrEditing = true
                },
                onBack = {
                    selectedCategory = null
                }
            )
        }
        else -> {
            CategoryListScreen(
                categories = categories,
                onAddCategory = { isAddingOrEditing = true },
                onCategorySelected = { category -> selectedCategory = category }
            )
        }
    }
}