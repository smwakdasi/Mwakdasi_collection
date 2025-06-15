package com.example.mwakdasicollection.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mwakdasicollection.MainActivity

@Composable
fun LoginScreen(navController: NavController) {
    // State variables for input, loading, and error messages
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text("LOGIN", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(20.dp))

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // Display an error message if login fails
        errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login button with loading logic
        Button(
            onClick = {
                // Input validation TODO: Add users to implement login functionality
                if (email.isNotBlank() && password.isNotBlank()) {
                    loading = true
                    errorMessage = null

                    // navigate to MainActivity
                    navController.context.startActivity(Intent(navController.context, MainActivity::class.java))

//                    // Firebase authentication
//                    FirebaseAuth.getInstance()
//                        .signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener { task ->
//                            loading = false
//                            if (task.isSuccessful) {
//                                // Navigate to HomeScreen on success
//                                Log.d("LoginScreen", "Login successful for user: $email")
//                                navController.navigate("HomeScreen")
//                            } else {
//                                // Display Firebase-specific error message
//                                errorMessage = task.exception?.localizedMessage ?: "Login failed."
//                                Log.e("LoginScreen", "Error: ${task.exception?.message}")
//                            }
//                        }
                } else {
                    errorMessage = "Email and password must not be empty."
                }
                // navigate to MainActivity


            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading // Disable the button while loading
        ) {
            if (loading) {
                // Show loading spinner when login is in progress
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                // Default button text
                Text("Login")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Navigate to SignupScreen TODO: FIX THE NAVIGATION
        TextButton(onClick = { navController.navigate("SignupScreen") }) {
            Text("Don't have an account? Sign Up")
        }
    }
}

//@Preview
//@Composable
//fun LoginScreenPreview() {
//     Preview of the LoginScreen
//    LoginScreen(navController = rememberNavController()) // Replace with a valid NavController in real use
//}