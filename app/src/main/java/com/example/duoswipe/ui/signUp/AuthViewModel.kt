package com.example.duoswipe.ui.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.duoswipe.data.model.Response
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    val oneTapClient: SignInClient
) : ViewModel() {

    val currentUser = getAuthState()

    init {
        getAuthState()
        CoroutineScope(Dispatchers.IO).launch {
            repository.verifyGoogleSignIn()
        }
    }

    private fun getAuthState() = repository.getAuthState(viewModelScope)

    fun oneTapSignIn() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.oneTapSignInResponse = Response.Loading
        DataProvider.oneTapSignInResponse = repository.oneTapSignIn()
    }

    fun signOut() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.signOutResponse = Response.Loading
        DataProvider.signOutResponse = repository.signOut()
    }

    fun signInWithGoogle(credentials: SignInCredential) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.googleSignInResponse = Response.Loading
        DataProvider.googleSignInResponse = repository.signInWithGoogle((credentials))
    }

    fun checkNeedsReAuth() = CoroutineScope(Dispatchers.IO).launch {
        if (repository.checkNeedsReAuth()) {
            val idToken = repository.authorizeGoogleSignIn()
            if (idToken != null) {
                deleteAccount(null)
            }
        }
    }

    fun deleteAccount(googleIdToken: String?) = CoroutineScope(Dispatchers.IO).launch {
        Log.i("AuthViewModel:deleteAccount", "Deleting Account...")
        DataProvider.deleteAccountResponse = Response.Loading
        DataProvider.deleteAccountResponse = repository.deleteUserAccount(googleIdToken)
    }
}