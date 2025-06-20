package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Address

@Composable
fun AddressList(
    addresses: List<Address>,
    onAddressSelected: (Address) -> Unit,    // Callback when an address is selected
    onSetDefault: (Address) -> Unit         // Callback to handle setting default address
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Space between items
    ) {
        items(addresses) { address ->
            AddressItem(
                address = address,
                onClick = onAddressSelected,
                onSetDefault = onSetDefault
            )
        }
    }
}