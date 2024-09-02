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
import com.example.duoswipe.data.model.AuthState
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.ui.profile.ProfileScreen
import com.example.duoswipe.ui.profile.ProfileViewModel
import com.example.duoswipe.ui.signIn.SignInScreen
import com.example.duoswipe.ui.signIn.SignInViewModel
import com.example.duoswipe.ui.signUp.SignUpScreen
import com.example.duoswipe.ui.signUp.SignUpViewModel
import com.example.duoswipe.ui.theme.DuoSwipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val signInViewModel by viewModels<SignInViewModel>()

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
                val currentUser = signUpViewModel.currentUser.collectAsState().value
                DataProvider.updateAuthState(currentUser)

                Log.i("AuthRepo", "Authenticated: ${DataProvider.isAuthenticated}")
                Log.i("AuthRepo", "Anonymous: ${DataProvider.isAnonymous}")
                Log.i("AuthRepo", "User: ${DataProvider.user}")

                val navController = rememberNavController()
                /*if (DataProvider.authState != AuthState.SignedOut) {
                    ProfileScreen(profileViewModel)
                } else {
                    SignUpScreen(signUpViewModel)
                }*/

                if (DataProvider.authState != AuthState.SignedOut) {
                    ProfileScreen(profileViewModel)
                } else {
                    SignInScreen(signInViewModel)
                }

                //SignInScreen(signInViewModel)
                //SignUpScreen(signUpViewModel)
            }
        }
    }
}

