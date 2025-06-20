package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Admin

@Composable
fun AdminForm(
    admin: Admin?,                             // Optional: Null for new admin, supplied for edit
    onSaveAdmin: (Admin) -> Unit               // Callback when the admin form is saved
) {
    var name by remember { mutableStateOf(admin?.name ?: "") }
    var email by remember { mutableStateOf(admin?.email ?: "") }
    var phoneNumber by remember { mutableStateOf(admin?.phoneNumber ?: "") }
    var role by remember { mutableStateOf(admin?.role ?: "Administrator") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = role,
            onValueChange = { role = it },
            label = { Text("Role") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && email.isNotBlank() && email.contains("@")) {
                    onSaveAdmin(
                        Admin(
                            adminId = admin?.adminId.orEmpty(),
                            name = name,
                            email = email,
                            phoneNumber = phoneNumber,
                            role = role,
                            isActive = admin?.isActive ?: true
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Admin")
        }
    }
}