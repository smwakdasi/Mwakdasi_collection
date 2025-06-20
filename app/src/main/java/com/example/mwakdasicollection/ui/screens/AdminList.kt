package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Admin

@Composable
fun AdminList(
    admins: List<Admin>,
    onAdminSelected: (Admin) -> Unit,           // Callback when an admin is selected from the list
    onToggleActiveStatus: (Admin) -> Unit       // Callback to activate/deactivate admins
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(admins) { admin ->
            AdminItem(
                admin = admin,
                onClick = onAdminSelected,
                onToggleActiveStatus = onToggleActiveStatus
            )
        }
    }
}