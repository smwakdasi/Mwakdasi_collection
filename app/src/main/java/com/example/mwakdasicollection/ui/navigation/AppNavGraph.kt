package com.example.mwakdasicollection.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mwakdasicollection.ui.screens.SignupScreen
import com.example.mwakdasicollection.ui.screens.LoginScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "SignupScreen") {
        composable("SignupScreen") {
            LoginScreen(navController)
        }
        // Add other composable destinations here
        composable("LoginScreen") {
             SignupScreen(navController)
        }
    }
}
