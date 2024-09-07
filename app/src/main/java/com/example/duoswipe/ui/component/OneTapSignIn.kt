package com.example.duoswipe.ui.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.model.DataProvider
import com.google.android.gms.auth.api.identity.BeginSignInResult
@Composable
fun OneTapSignIn(
    launch: (result: BeginSignInResult) -> Unit,
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val oneTapSignInResponse = DataProvider.oneTapSignInResponse) {
        is Response.Loading ->  {
            Log.i("Login:OneTap", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> oneTapSignInResponse.data?.let { signInResult ->
            LaunchedEffect(signInResult) {
                launch(signInResult)
            }
        }
        is Response.Failure -> oneTapSignInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}