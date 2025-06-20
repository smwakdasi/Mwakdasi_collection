package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Address

@Composable
fun AddressForm(
    address: Address?,                     // Optional: null for new, provide one for editing
    onSaveAddress: (Address) -> Unit       // Callback when saving the address
) {
    var street by remember { mutableStateOf(address?.street ?: "") }
    var city by remember { mutableStateOf(address?.city ?: "") }
    var state by remember { mutableStateOf(address?.state ?: "") }
    var zipCode by remember { mutableStateOf(address?.zipCode ?: "") }
    var country by remember { mutableStateOf(address?.country ?: "Kenya") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = street,
            onValueChange = { street = it },
            label = { Text("Street") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state,
            onValueChange = { state = it },
            label = { Text("State") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = zipCode,
            onValueChange = { zipCode = it },
            label = { Text("Zip Code") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (street.isNotBlank() && city.isNotBlank() && zipCode.isNotBlank()) {
                    onSaveAddress(
                        Address(
                            addressId = address?.addressId.orEmpty(),
                            userId = address?.userId.orEmpty(),
                            street = street,
                            city = city,
                            state = state,
                            zipCode = zipCode,
                            country = country,
                            isDefault = address?.isDefault ?: false
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Address")
        }
    }
}