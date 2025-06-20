package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Review

@Composable
fun ReviewListScreen(
    reviews: List<Review>,
    onReviewClick: (Review) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Reviews") })
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(reviews) { review ->
                    ReviewCard(review = review, onClick = { onReviewClick(review) })
                }
            }
        }
    )
}

@Composable
fun ReviewCard(
    review: Review,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Rating: ${review.rating}", style = MaterialTheme.typography.bodyLarge)
            if (review.hasComment()) {
                Text(
                    text = "\"${review.comment}\"",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "Posted on: ${review.getFormattedTimestamp()}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}