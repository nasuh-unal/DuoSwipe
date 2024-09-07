package com.example.duoswipe.ui.signIn.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator
import com.example.duoswipe.ui.signIn.SignInViewModel

@Composable
fun SignIn(
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val signInResponse = DataProvider.signInResponse) {
        is Response.Loading -> {
            Log.i("Login:OneTap", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> Unit
        is Response.Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}