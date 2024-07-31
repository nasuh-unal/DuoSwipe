package com.example.duoswipe.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.ui.login.LoginScreen
import com.example.duoswipe.ui.signUp.AuthViewModel
import com.example.duoswipe.ui.signUp.SignUpScreen
import com.example.duoswipe.ui.theme.DuoSwipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            DuoSwipeTheme {
                val currentUser = authViewModel.currentUser.collectAsState().value
                DataProvider.updateAuthState(currentUser)

                Log.i("AuthRepo", "Authenticated: ${DataProvider.isAuthenticated}")
                Log.i("AuthRepo", "Anonymous: ${DataProvider.isAnonymous}")
                Log.i("AuthRepo", "User: ${DataProvider.user}")

                val navController = rememberNavController()
                SignUpScreen()
//                if (DataProvider.authState != AuthState.SignedOut) {
//                    ProfileScreen(authViewModel)
//                } else {
//                    RegisterScreen(authViewModel)
//                }
            }
        }
    }
}

