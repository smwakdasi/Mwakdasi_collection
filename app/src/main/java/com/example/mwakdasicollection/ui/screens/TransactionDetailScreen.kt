package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transaction: Transaction,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction Details") },
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
                    .padding(16.dp)
            ) {
                Text(
                    text = "Transaction Summary",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Transaction ID: ${transaction.transactionId}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Amount: ${transaction.totalAmount} ${transaction.currency}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Status: ${transaction.status}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Date: ${transaction.getFormattedTimestamp()}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onBack) {
                    Text("Back")
                }
            }
        }
    )
}