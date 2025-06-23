package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Transactions") })
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionCard(transaction = transaction, onClick = { onTransactionClick(transaction) })
                }
            }
        }
    )
}

@Composable
fun TransactionCard(transaction: Transaction, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Transaction ID: ${transaction.transactionId}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Amount: ${transaction.totalAmount} ${transaction.currency}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${transaction.status}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Date: ${transaction.getFormattedTimestamp()}", style = MaterialTheme.typography.bodySmall)
        }
    }
}