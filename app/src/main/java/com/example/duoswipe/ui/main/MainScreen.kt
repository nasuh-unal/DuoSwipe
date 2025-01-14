package com.example.duoswipe.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.duoswipe.core.Constants.CARD_KEY
import com.example.duoswipe.data.model.AuthState
import com.example.duoswipe.data.model.Constants.CARDLISTOVERVIEW_SCREEN
import com.example.duoswipe.data.model.Constants.SIGN_IN_SCREEN
import com.example.duoswipe.data.model.Constants.FORGOT_PASSWORD_SCREEN
import com.example.duoswipe.data.model.Constants.OVERVIEW_SCREEN
import com.example.duoswipe.data.model.Constants.PROFILE_SCREEN
import com.example.duoswipe.data.model.Constants.SIGN_UP_SCREEN
import com.example.duoswipe.data.model.Constants.VERIFY_EMAIL_SCREEN
import com.example.duoswipe.data.model.DataProvider.authState
import com.example.duoswipe.ui.cardListOverview.CardListOverviewScreen
import com.example.duoswipe.ui.forgotPassword.ForgotPasswordScreen
import com.example.duoswipe.ui.overview.OverviewScreen
import com.example.duoswipe.ui.profile.ProfileScreen
import com.example.duoswipe.ui.signIn.SignInScreen
import com.example.duoswipe.ui.signUp.SignUpScreen

sealed class Screen(val route: String) {
    object SignInScreen : Screen(SIGN_IN_SCREEN)
    object ForgotPasswordScreen : Screen(FORGOT_PASSWORD_SCREEN)
    object SignUpScreen : Screen(SIGN_UP_SCREEN)
    object VerifyEmailScreen : Screen(VERIFY_EMAIL_SCREEN)
    object ProfileScreen : Screen(PROFILE_SCREEN)
    object OverviewScreen : Screen(OVERVIEW_SCREEN)
    object CardListOverviewScreen : Screen(CARDLISTOVERVIEW_SCREEN)
}

@ExperimentalComposeUiApi
@Composable
fun MainScreen(navController: NavHostController) {
    val startDestination =
        if (authState == AuthState.SignedIn) {
            Screen.OverviewScreen.route
        } else {
            Screen.SignInScreen.route
        }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(
                navigateToSignUpOrSignInScreen = { navController.navigate(Screen.SignUpScreen.route) },
                navigateToForgotPasswordScreen = { navController.navigate(Screen.ForgotPasswordScreen.route) })
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navigateToSignUpOrSignInScreen = { navController.navigate(Screen.SignInScreen.route) })
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(
                navigateToOverviewScreen = { navController.navigate(Screen.OverviewScreen.route) },
                navigateToProfileScreen = { navController.navigate(Screen.ProfileScreen.route) }
            )
        }
        composable(route = Screen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen()
        }
        composable(route = Screen.OverviewScreen.route) {
            OverviewScreen(
                navigateToOverviewScreen = { navController.navigate(Screen.OverviewScreen.route) },
                navigateToProfileScreen = { navController.navigate(Screen.ProfileScreen.route) },
                navigateToCardListOverviewScreen = {
                    navController.navigate("${Screen.CardListOverviewScreen.route}/${it}")
                }
            )
        }
        composable(
            route = "${Screen.CardListOverviewScreen.route}/{$CARD_KEY}",
            arguments = mutableStateListOf(
                navArgument(CARD_KEY) {
                    type = StringType
                }
            )
        ) { backStackEntry ->
            val cardKey = backStackEntry.arguments?.getString(CARD_KEY) ?: "NO_VALUE"
            CardListOverviewScreen(
                cardKey,
                { navController.navigate(Screen.ProfileScreen.route) },
                { navController.navigate(Screen.OverviewScreen.route) }
            )
        }
    }
}