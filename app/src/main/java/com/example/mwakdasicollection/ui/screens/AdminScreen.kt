package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.mwakdasicollection.model.Admin

@OptIn(ExperimentalMaterial3Api::class)
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
                    onToggleActiveStatus = onToggleActiveStatus,
                    modifier = Modifier.padding(padding)              // Apply padding to the list
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