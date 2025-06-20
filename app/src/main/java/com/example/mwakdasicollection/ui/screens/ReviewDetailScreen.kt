package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Review

@Composable
fun ReviewDetailScreen(
    review: Review,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rating: ${review.rating}",
                    style = MaterialTheme.typography.headlineMedium
                )
                if (review.hasComment()) {
                    Text(
                        text = "Comment: ${review.comment}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                Text(
                    text = "Review Date: ${review.getFormattedTimestamp()}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 32.dp)
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = { onBack() }) {
                    Text("Back")
                }
            }
        }
    )
}