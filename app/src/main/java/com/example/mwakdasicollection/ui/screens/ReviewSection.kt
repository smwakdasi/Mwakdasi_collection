package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Review
import com.example.mwakdasicollection.repositories.ReviewRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun ReviewSection(
    productId: String,
    firestore: FirebaseFirestore
) {
    // Initialize the repository instance
    val reviewRepository = remember { ReviewRepository(firestore) }
    val coroutineScope = rememberCoroutineScope()

    // State for the list of reviews
    var reviewList by remember { mutableStateOf<List<Review>>(emptyList()) }

    // States for review submission
    var ratingInput by remember { mutableStateOf("") }
    var commentInput by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    // Observe reviews from the repository via a Flow
    LaunchedEffect(productId) {
        reviewRepository.getReviews(productId).collect { reviews ->
            reviewList = reviews
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Reviews", style = MaterialTheme.typography.titleLarge)
        if (reviewList.isEmpty()) {
            Text("No reviews yet", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(reviewList) { review ->
                    ReviewCard(review = review, onClick = { /* Optional: navigate to review detail */ })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Submit a Review", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = ratingInput,
            onValueChange = { ratingInput = it },
            label = { Text("Rating (1-5)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = commentInput,
            onValueChange = { commentInput = it },
            label = { Text("Comment") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val rating = ratingInput.toFloatOrNull() ?: 0f
                if (rating in 1f..5f) {
                    isSubmitting = true
                    val newReview = Review(rating = rating, comment = commentInput)
                    coroutineScope.launch {
                        reviewRepository.submitReview(productId, newReview)
                        // Reset form fields after success
                        ratingInput = ""
                        commentInput = ""
                        isSubmitting = false
                    }
                }
            },
            enabled = !isSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}