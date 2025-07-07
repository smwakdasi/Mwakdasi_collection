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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    firestore: FirebaseFirestore,
    navController: NavController // Allows navigation to ProductItemScreen
) {
    // State management for search query, products, loading, and error states
    var searchQuery by remember { mutableStateOf("") }
    val productList = remember { mutableStateListOf<Product>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch products from Firestore when the screen is displayed
    LaunchedEffect(Unit) {
        fetchProductsFromFirestore(
            firestore = firestore,
            productList = productList,
            onLoading = { isLoading = it },
            onError = { error -> errorMessage = error.message }
        )
    }

    // Filter products based on the search query
    val filteredProducts = if (searchQuery.isEmpty()) {
        productList
    } else {
        productList.filter { it.name?.contains(searchQuery, ignoreCase = true) == true }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                // Search field for filtering products
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search Products") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    when {
                        isLoading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        !errorMessage.isNullOrEmpty() -> {
                            ErrorScreen(message = errorMessage!!) {
                                // Retry logic can be implemented as needed
                            }
                        }
                        else -> {
                            // Display filtered product list using LazyColumn
                            LazyColumn(
                                contentPadding = PaddingValues(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filteredProducts) { product ->
                                    ProductCard(
                                        product = product,
                                        onClick = {
                                            // Navigate to product detail screen
                                            navController.navigate("product_item/${product.id ?: ""}")
                                        }
                                    )
                                }
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
        val result = firestore.collection("PRODUCTS").get().await()
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
            // Display the image if there's a valid URL
            product.imageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = product.name ?: "Product Image",
                    modifier = Modifier
                        // You can change the size for a thumbnail or a bigger image
                        //.size(80.dp)  // For a small preview image
                        .fillMaxWidth()
                        .height(150.dp) // For a larger image display
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
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
                text = product.price?.let { "KSH %.2f".format(it) } ?: "N/A",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}