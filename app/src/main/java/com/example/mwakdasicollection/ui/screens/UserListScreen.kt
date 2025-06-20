package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.User

@Composable
fun UserListScreen(
    users: List<User>,
    onUserClick: (User) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User List") })
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(users) { user ->
                    UserCard(user = user, onClick = { onUserClick(user) })
                }
            }
        }
    )
}

@Composable
fun UserCard(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Name: ${user.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
            }
            Text(
                text = user.getInitials(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}