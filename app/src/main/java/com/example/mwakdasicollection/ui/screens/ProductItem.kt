package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.NavController
import kotlinx.coroutines.tasks.await
import okhttp3.internal.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItemScreen(
    productId: String,
    firestore: FirebaseFirestore,
    navController: NavController
) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch product details when the screen is opened
    LaunchedEffect(productId) {
        try {
            val document = firestore.collection("products").document(productId).get().await()
            product = document.toObject(Product::class.java)
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            errorMessage = e.message
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    !errorMessage.isNullOrEmpty() -> {
                        ErrorScreen(
                            message = errorMessage!!,
                            onRetry = {
                                isLoading = true
                                errorMessage = null
                                // Retry fetching the product
                                LaunchedEffect(productId) {
                                    try {
                                        val document = firestore.collection("products").document(productId).get().await()
                                        product = document.toObject(Product::class.java)
                                        isLoading = false
                                    } catch (e: Exception) {
                                        isLoading = false
                                        errorMessage = e.message
                                    }
                                }
                            }
                        )
                    }

                    product != null -> {
                        ProductDetails(product = product!!)
                    }
                }
            }
        }
    )
}

/**
 * Displays the details of the product.
 */
@Composable
fun ProductDetails(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // Product Name
        Text(
            text = product.name ?: "Unknown Product",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Product Description
        Text(
            text = product.description ?: "No description.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Product Price
        Text(
            text = format(product.price.toString()) ?: "Price not available",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Additional Information
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = "Additional information about the product can go here.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

/**
 * Error screen with retry functionality.
 */
@Composable
fun ErrorScreen(message: String, onRetry: @Composable () -> Unit) {
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
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry as () -> Unit) {
            Text(text = "Retry")
        }
    }
}