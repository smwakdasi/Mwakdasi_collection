package com.example.mwakdasicollection.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.mwakdasicollection.model.Admin

@Composable
fun AdminScreen(
    admins: List<Admin>,                         // List of all admins
    onAddOrEditAdmin: (Admin) -> Unit,           // Callback for saving admins
    onToggleActiveStatus: (Admin) -> Unit        // Callback for toggling active status
) {
    var editingAdmin by remember { mutableStateOf<Admin?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Admin Management") })
        },
        content = { padding ->
            if (editingAdmin == null) {
                AdminList(
                    admins = admins,
                    onAdminSelected = { editingAdmin = it },          // Trigger edit mode
                    onToggleActiveStatus = onToggleActiveStatus
                )
            } else {
                AdminForm(
                    admin = editingAdmin,
                    onSaveAdmin = {
                        onAddOrEditAdmin(it)
                        editingAdmin = null                             // Exit edit mode
                    }
                )
            }
        }
    )
}