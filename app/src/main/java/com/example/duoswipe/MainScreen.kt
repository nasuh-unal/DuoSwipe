package com.example.duoswipe

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.duoswipe.ui.login.LoginScreen

@Composable
fun MainScreen(){
    val navController = rememberNavController()
        NavHost(
            navController=navController,
            startDestination="login_screen"
        ){
            composable("login_screen") {
                LoginScreen(navController)
            }
        }
}