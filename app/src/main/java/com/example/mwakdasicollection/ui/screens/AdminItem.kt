package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Admin

@Composable
fun AdminItem(
    admin: Admin,
    onClick: (Admin) -> Unit,                  // Callback for admin selection
    onToggleActiveStatus: (Admin) -> Unit     // Callback to activate/deactivate admin
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick(admin) },
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = admin.getAdminInfo(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (admin.isActive) "Active" else "Inactive",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (admin.isActive) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }

            Button(
                onClick = { onToggleActiveStatus(admin) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (admin.isActive) Color.Red else Color.Green,
                    contentColor = Color.White
                )
            ) {
                Text(text = if (admin.isActive) "Deactivate" else "Activate")
            }
        }
    }
}