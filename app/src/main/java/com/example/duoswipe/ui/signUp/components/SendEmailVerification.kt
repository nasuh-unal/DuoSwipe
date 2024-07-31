package com.example.duoswipe.ui.signUp.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator

@Composable
fun SendEmailVerification() {
    when(val sendEmailVerificationResponse = DataProvider.sendEmailVerificationResponse) {
        is Response.Loading -> {
            Log.i("Login:OneTap", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> Unit
        is Response.Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}