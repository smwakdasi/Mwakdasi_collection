package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mwakdasicollection.model.Cart

@Composable
fun CartList(
    cartItems: List<Cart>,
    onQuantityChange: (Cart, Int) -> Unit, // Callback to change quantity
    onRemoveItem: (Cart) -> Unit          // Callback to remove an item
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        items(cartItems) { cartItem ->
            CartItem(
                cartItem = cartItem,
                onQuantityChange = onQuantityChange,
                onRemoveItem = onRemoveItem
            )
            Divider() // To visually separate each CartItem
        }
    }
}