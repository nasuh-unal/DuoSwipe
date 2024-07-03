package com.example.duoswipe.data.repository

import com.example.duoswipe.data.model.AuthStateResponse
import com.example.duoswipe.data.model.DeleteAccountResponse
import com.example.duoswipe.data.model.FirebaseSignInResponse
import com.example.duoswipe.data.model.OneTapSignInResponse
import com.example.duoswipe.data.model.SignOutResponse
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.CoroutineScope

interface AuthRepository {
    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun verifyGoogleSignIn(): Boolean
    suspend fun signInAnonymously(): FirebaseSignInResponse
    suspend fun onTapSignIn(): OneTapSignInResponse
    suspend fun signOut(): SignOutResponse
    suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse
    suspend fun authorizeGoogleSignIn(): String?
    suspend fun deleteUserAccount(googleIdToken: String?): DeleteAccountResponse
    fun checkNeedsReAuth(): Boolean
}