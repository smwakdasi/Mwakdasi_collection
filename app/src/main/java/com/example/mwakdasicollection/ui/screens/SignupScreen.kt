package com.example.mwakdasicollection.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mwakdasicollection.repositories.AuthRepositoryImpl
import com.example.mwakdasicollection.viewmodel.AuthViewModel
import com.example.mwakdasicollection.viewmodel.AuthViewModelFactory

@Composable
fun SignupScreen(navController: NavController) {
    // State variables for input, loading, and error messages
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // inject the AuthViewModel
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
            "SIGNUP",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Username input field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = MaterialTheme.colorScheme.primary) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent
            )
        )

        // Display an error message if signup fails
        errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Signup button with loading logic
        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    loading = true
                    errorMessage = null

                    // Delegate the signup logic to the AuthViewModel
                    authViewModel.signup(
                        email = email,
                        password = password,
                        onSuccess = {
                            loading = false
                            Log.d("SignupScreen", "Signup successful for user: $email")
                            // After a successful signup, navigate to the home screen, for instance
                            navController.navigate("home") {
                                popUpTo("signup") { inclusive = true }
                            }
                        },
                        onError = { error ->
                            loading = false
                            errorMessage = error.localizedMessage ?: "Signup failed."
                            Log.e("SignupScreen", "Error: ${error.message}")
                        }
                    )
                } else {
                    errorMessage = "Email and password must not be empty."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading // Disable the button while loading
        ) {
            if (loading) {
                // Show loading spinner when signup is in progress
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                // Default button text
                Text("SIGNUP")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Navigate to LoginScreen
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Login")
        }
    }
}

//@Preview
//@Composable
//fun SignupScreenPreview() {
//    // Preview of the LoginScreen
//    SignupScreen(navController = rememberNavController()) // Replace with a valid NavController in real use
//}