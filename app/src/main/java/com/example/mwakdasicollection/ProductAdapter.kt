package com.example.mwakdasicollection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mwakdasicollection.model.Product

class ProductAdapter(
    private val productList: List<Product>, // List of products
    private val onProductClick: (Product) -> Unit // Callback for item click
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // ViewHolder class to bind individual product items
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.productName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.productDescription)
        val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.apply {
            // Bind product data to item view
            nameTextView.text = product.name
            descriptionTextView.text = product.description
            priceTextView.text = "KSh ${product.price}"

            // Set click listener on the entire item view
            holder.itemView.setOnClickListener {
                onProductClick(product) // Trigger the click callback
            }
        }
    }

    override fun getItemCount(): Int = productList.size
}