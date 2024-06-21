package com.example.duoswipe.ui.main

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.duoswipe.ui.login.LoginScreen
import com.example.duoswipe.ui.register.GoogleAuthUiClient
import com.example.duoswipe.ui.register.RegisterScreen
import androidx.lifecycle.lifecycleScope
import com.example.duoswipe.ui.register.RegisterViewModel


@Composable
fun MainScreen(
    googleAuthUiClient: GoogleAuthUiClient
) {
    val navController = rememberNavController()



    NavHost(
        navController = navController, startDestination = "login_screen"
    ) {
        composable("login_screen") {
            LoginScreen(navController)
        }
        composable("register_screen") {

            val viewModel = viewModel<RegisterViewModel>()
            val state by viewModel.signInState.collectAsState()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignInUser() != null) {
                    navController.navigate("profile")
                }

            }

            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    })

            RegisterScreen(navController)
        }
    }
}