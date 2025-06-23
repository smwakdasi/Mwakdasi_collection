package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Notification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailScreen(
    notification: Notification,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(notification.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Back to List")
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
                    text = notification.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Sent on: ${notification.getFormattedTimestamp()}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                if (notification.isUnread()) {
                    Button(onClick = { /* Optionally handle marking as read here */ }) {
                        Text("Mark as Read")
                    }
                }
            }
        }
    )
}