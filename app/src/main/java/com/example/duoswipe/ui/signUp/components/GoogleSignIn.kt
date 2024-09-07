package com.example.duoswipe.ui.signUp.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator

@Composable
fun GoogleSignIn(
    launch: () -> Unit,
    //showErrorMessage: (errorMessage: String?) -> Unit
) {
    when (val signInWithGoogleResponse = DataProvider.googleSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:GoogleSignIn", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> signInWithGoogleResponse.data?.let { authResult ->
            Log.i("Login:GoogleSignIn", "Success: $authResult")
            launch()
        }
        is Response.Failure -> signInWithGoogleResponse.apply {
            LaunchedEffect(e) {
                print(e)
                //showErrorMessage(e.message)
            }
        }
    }
}