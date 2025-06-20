package com.example.mwakdasicollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mwakdasicollection.model.Product
import com.example.mwakdasicollection.ui.navigation.AppNavGraph
import com.example.mwakdasicollection.ui.theme.MwakdasiCollectionTheme
import com.example.mwakdasicollection.repositories.AuthRepositoryImpl
import com.example.mwakdasicollection.viewmodel.AuthViewModel
import com.example.mwakdasicollection.viewmodel.AuthViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    // Firestore instance
    private val firestore: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Wrap main UI in the app's theme
            MwakdasiCollectionTheme {
                val authViewModel: AuthViewModel = viewModel(
                    factory = AuthViewModelFactory(AuthRepositoryImpl())
                )

                // Observe authentication state
                val isUserAuthenticated by authViewModel.isUserAuthenticated.collectAsState(initial = false)

                // App Navigation: Send user to the appropriate flow depending on their authentication state
                if (isUserAuthenticated) {
                    ProductScreen(firestore = firestore) // Show product list if authenticated
                } else {
                    AppNavGraph(isUserAuthenticated = false) // Show login/signup navigation graph
                }
            }
        }
    }
}