package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mwakdasicollection.model.Product
import com.example.mwakdasicollection.ui.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun ProductListScreen(
    firestore: FirebaseFirestore,
    navController: NavController // Allows navigation to ProductItemScreen
) {
    // State management for products, loading, and error states
    val productList = remember { mutableStateListOf<Product>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Fetch products from Firestore when the screen is displayed
    LaunchedEffect(Unit) {
        fetchProductsFromFirestore(
            firestore = firestore,
            productList = productList,
            onLoading = { isLoading = it },
            onError = { error -> errorMessage = error.message }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    !errorMessage.isNullOrEmpty() -> {
                        ErrorScreen(message = errorMessage!!) {
                            // Retry the fetch logic using a coroutine scope
                            scope.launch {
                                fetchProductsFromFirestore(
                                    firestore = firestore,
                                    productList = productList,
                                    onLoading = { isLoading = it },
                                    onError = { error -> errorMessage = error.message }
                                )
                            }
                        }
                    }

                    else -> {
                        // Product list displayed using LazyColumn
                        LazyColumn(
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(productList) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = {
                                        // Navigate to ProductItemScreen, passing product details
                                        navController.navigate(
                                            Screen.ProductItemScreen.withArgs(product.id ?: "")
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * Function to fetch products from Firestore and update UI state.
 */
suspend fun fetchProductsFromFirestore(
    firestore: FirebaseFirestore,
    productList: MutableList<Product>,
    onLoading: (Boolean) -> Unit = {},
    onError: (Exception) -> Unit = {}
) {
    onLoading(true) // Show loading indicator
    try {
        val result = firestore.collection("products").get().await()
        productList.clear()
        for (document in result) {
            val product = document.toObject(Product::class.java)
            if (product != null) {
                productList.add(product)
            }
        }
    } catch (e: Exception) {
        onError(e) // Handle errors (e.g., network issues)
    } finally {
        onLoading(false) // Stop loading indicator
    }
}

/**
 * Composable for an error screen with retry functionality.
 */
@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

/**
 * Card for displaying a single product, with navigation for product details.
 */
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name ?: "Unknown Product",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.formattedPrice() ?: "N/A",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}