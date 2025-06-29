package com.example.mwakdasicollection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mwakdasicollection.repositories.AuthRepositoryImpl
import com.example.mwakdasicollection.viewmodel.AuthViewModel
import com.example.mwakdasicollection.viewmodel.AuthViewModelFactory

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    // State variables for email, password, loading, and error
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Create the view model using the proper factory
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(AuthRepositoryImpl())
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "LOGIN",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display error message if exists
        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    loading = true
                    errorMessage = null
                    // Invoke the ViewModel's login method
                    authViewModel.login(
                        email = email,
                        password = password,
                        onSuccess = {
                            loading = false
                            onLoginSuccess()
                        },
                        onError = { error ->
                            loading = false
                            errorMessage = error.localizedMessage ?: "Login failed."
                        }
                    )
                } else {
                    errorMessage = "Email and password must not be empty."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading // Disable button during loading
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Login") // Default Button Text
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Signup navigation
        TextButton(
            onClick = { navController.navigate("signup") } // Navigate to SignupScreen
        ) {
            Text("Don't have an account? Sign Up")
        }
    }
}