package com.example.mwakdasicollection.ui.screens

import ProductDetailScreen
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mwakdasicollection.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun ProductDetailWrapper(
    productId: String,
    firestore: FirebaseFirestore,
    onBack: () -> Unit
) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    // Define a log tag for easier filtering
    val TAG = "ProductDetailWrapper"

    // Log the productId being used for the query.
    Log.d(TAG, "Requested productId: $productId")

    LaunchedEffect(productId) {
        try {
            // Option 2: Query by the "id" field inside the document instead of using document id.
            val querySnapshot = firestore.collection("PRODUCTS")
                .whereEqualTo("id", productId)
                .get()
                .await()

            if (querySnapshot.documents.isNotEmpty()) {
                val document = querySnapshot.documents.first()
                // Log the raw document data for debugging.
                Log.d(TAG, "Fetched document data: ${document.data}")

                val prod = document.toObject(Product::class.java)
                if (prod == null) {
                    Log.w(TAG, "Mapping to Product returned null")
                } else {
                    Log.d(TAG, "Mapped Product: $prod")
                }
                // If the "id" property from Firestore is already set, you may not need to copy the document ID.
                product = prod
                Log.d(TAG, "Final Product object: $product")
            } else {
                Log.w(TAG, "No product found with id: $productId")
                errorMessage = "Product with id $productId not found."
            }
        } catch (e: Exception) {
            errorMessage = e.message
            Log.e(TAG, "Error fetching product", e)
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error loading product: $errorMessage")
            }
        }
        product != null -> {
            ProductDetailScreen(
                product = product!!,
                firestore = firestore,
                onBack = onBack
            )
        }
    }
}