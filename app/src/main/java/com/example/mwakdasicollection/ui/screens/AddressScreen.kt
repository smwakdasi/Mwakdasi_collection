package com.example.mwakdasicollection.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.mwakdasicollection.model.Address

@Composable
fun AddressScreen(
    addresses: List<Address>,
    onAddOrEditAddress: (Address) -> Unit,
    onSetDefaultAddress: (Address) -> Unit
) {
    var editingAddress by remember { mutableStateOf<Address?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Addresses") })
        },
        content = { padding ->
            if (editingAddress == null) {
                AddressList(
                    addresses = addresses,
                    onAddressSelected = { editingAddress = it },
                    onSetDefault = onSetDefaultAddress
                )
            } else {
                AddressForm(
                    address = editingAddress,
                    onSaveAddress = {
                        onAddOrEditAddress(it)
                        editingAddress = null
                    }
                )
            }
        }
    )
}