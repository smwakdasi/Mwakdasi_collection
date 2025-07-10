// MainActivity.kt
package com.example.mwakdasicollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mwakdasicollection.repositories.AuthRepositoryImpl
import com.example.mwakdasicollection.ui.navigation.AppNavGraph
import com.example.mwakdasicollection.ui.theme.MwakdasiCollectionTheme
import com.example.mwakdasicollection.viewmodel.AuthViewModel
import com.example.mwakdasicollection.viewmodel.AuthViewModelFactory
import com.google.firebase.FirebaseApp
//import com.google.firebase.appcheck.FirebaseAppCheck
//import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    // Declare Firestore as a late-initialized variable
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase first
        FirebaseApp.initializeApp(this)

        // Now initialize Firestore after Firebase is set up
        firestore = Firebase.firestore

//        TODO implement appcheck during production
//        val firebaseAppCheck = FirebaseAppCheck.getInstance()
//        firebaseAppCheck.installAppCheckProviderFactory(
//            SafetyNetAppCheckProviderFactory.getInstance()
//        )
        setContent {
            MwakdasiCollectionTheme {
                val authViewModel: AuthViewModel = viewModel(
                    factory = AuthViewModelFactory(AuthRepositoryImpl())
                )

                // Observe authentication state
                val isUserAuthenticated by authViewModel.isUserAuthenticated.collectAsState(initial = false)

                // Let the navigation graph handle which screen to show.
                AppNavGraph(
                    isUserAuthenticated = isUserAuthenticated,
                    firestore = firestore,
                    authViewModel = authViewModel
                )
            }
        }
    }
}