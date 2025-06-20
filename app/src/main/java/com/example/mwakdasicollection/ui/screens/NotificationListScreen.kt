package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Notification

@Composable
fun NotificationListScreen(
    notifications: List<Notification>,
    onNotificationClick: (Notification) -> Unit,
    onMarkAsRead: (Notification) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notifications") })
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notifications) { notification ->
                    NotificationCard(
                        notification = notification,
                        onClick = { onNotificationClick(notification) },
                        onMarkAsRead = { onMarkAsRead(notification) }
                    )
                }
            }
        }
    )
}

@Composable
fun NotificationCard(
    notification: Notification,
    onClick: () -> Unit,
    onMarkAsRead: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = notification.title, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(
                    text = notification.getFormattedTimestamp(),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row {
                if (notification.isUnread()) {
                    TextButton(onClick = onMarkAsRead) {
                        Text("Mark as Read")
                    }
                } else {
                    TextButton(onClick = onClick) {
                        Icon(Icons.Filled.Visibility, contentDescription = "View Details")
                    }
                }
            }
        }
    }
}