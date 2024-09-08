package com.example.duoswipe.ui.forgotPassword.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator

@Composable
fun ForgotPassword(
    showResetPasswordMessage: () -> Unit,
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when (val sendPasswordResetEmailResponse = DataProvider.sendPasswordResetEmailResponse) {
        is Response.Loading -> AuthLoginProgressIndicator()
        is Response.Success -> {
            val isPasswordResetEmailSent = sendPasswordResetEmailResponse.data
            LaunchedEffect(isPasswordResetEmailSent) {
                if (isPasswordResetEmailSent!!) {
                    showResetPasswordMessage()
                }
            }
        }

        is Response.Failure -> sendPasswordResetEmailResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}