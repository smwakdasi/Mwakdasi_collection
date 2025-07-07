package com.example.mwakdasicollection.providers

import com.example.mwakdasicollection.model.Cart
import kotlinx.coroutines.delay


// TODO to be deleted once samantha creates the real cart logic
class DummyCartProvider : CartProvider {
    override suspend fun getCartItems(): List<Cart> {
        // Simulate a short delay as if fetching data from a remote source
        delay(500)
        return listOf(
            Cart(
                productId = "1",
                productName = "Sample Product 1",
                quantity = 2,
                price = 50.0,
                imageUrl = "https://example.com/image1.jpg"
            ),
            Cart(
                productId = "2",
                productName = "Sample Product 2",
                quantity = 1,
                price = 75.0,
                imageUrl = "https://example.com/image2.jpg"
            )
        )
    }
}