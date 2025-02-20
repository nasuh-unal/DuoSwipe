package com.example.duoswipe.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.duoswipe.data.repository.SetCardListResponse
import com.google.firebase.auth.FirebaseUser
enum class AuthState {
    SignedIn, SignedOut;
}
//viewmodel aktar
object DataProvider {
    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(null))
    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
    var signUpResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Response.Success(null))
    var sendPasswordResetEmailResponse by mutableStateOf<SendPasswordResetEmailResponse>(Response.Success(false))
    var googleSignInResponse by mutableStateOf<FirebaseSignInResponse>(Response.Success(null))
    var user by mutableStateOf<FirebaseUser?>(null)
    var isAuthenticated by mutableStateOf(false)
    var deleteAccountResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(
        Response.Success(
            null
        )
    )

    var authState by mutableStateOf(AuthState.SignedOut)
        private set

    fun updateAuthState(user: FirebaseUser?) {
        this.user = user
        isAuthenticated = user != null

        authState = if (isAuthenticated) {
            AuthState.SignedIn
        } else {
            AuthState.SignedOut
        }
    }
}