package com.example.mwakdasicollection

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mwakdasicollection.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var progressBar: ProgressBar
    private val productList = mutableListOf<Product>()
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initializeViews()

        // Fetch data from Firestore
        fetchProductsFromFirestore()
    }

    /**
     * Initializes views, including RecyclerView and ProgressBar
     */
    private fun initializeViews() {
        recyclerView = findViewById(R.id.productRecyclerView)
        progressBar = findViewById(R.id.progress_bar)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(productList) { product ->
            // Handle product click
            Log.d(TAG, "Product clicked: ${product.name}")
        }
        recyclerView.adapter = adapter
    }

    /**
     * Fetch product data from Firebase Firestore and update RecyclerView
     */
    private fun fetchProductsFromFirestore() {
        showLoading(true)

        firestore.collection("products")
            .get()
            .addOnSuccessListener { result ->
                productList.clear()
                for (document in result) {
                    val product = document.toObject<Product>()
                    if (product != null) {
                        productList.add(product)
                    } else {
                        Log.w(TAG, "Skipped null product from Firestore document.")
                    }
                }
                adapter.notifyDataSetChanged()
                Log.d(TAG, "Fetched ${productList.size} products successfully.")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching products from Firestore.", exception)
            }
            .addOnCompleteListener {
                showLoading(false)
            }
    }

    /**
     * Shows or hides the progress bar
     * @param show Boolean indicating whether to show the progress bar
     */
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        recyclerView.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }
}