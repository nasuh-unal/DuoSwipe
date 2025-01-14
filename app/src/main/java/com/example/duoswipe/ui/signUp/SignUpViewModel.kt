package com.example.duoswipe.ui.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.AuthRepository
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    val oneTapClient: SignInClient
) : ViewModel() {

    //val currentUser = getAuthState()

    init {
        //getAuthState()
        CoroutineScope(Dispatchers.IO).launch {
            repository.verifyGoogleSignIn()
        }
    }

    //private fun getAuthState() = repository.getAuthState(viewModelScope)
    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        DataProvider.signUpResponse = Response.Loading
        DataProvider.signUpResponse = repository.firebaseSignUpWithEmailAndPassword(email, password)
    }

    fun oneTapSignIn() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.oneTapSignInResponse = Response.Loading
        DataProvider.oneTapSignInResponse = repository.oneTapSignIn()
    }

    fun sendEmailVerification() = viewModelScope.launch {
        DataProvider.sendEmailVerificationResponse = Response.Loading
        DataProvider.sendEmailVerificationResponse = repository.sendEmailVerification()
    }

    fun signInWithGoogle(credentials: SignInCredential) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.googleSignInResponse = Response.Loading
        DataProvider.googleSignInResponse = repository.signInWithGoogle((credentials))
    }

    fun checkNeedsReAuth() = CoroutineScope(Dispatchers.IO).launch {
        if (repository.checkNeedsReAuth()) {
            // Authorize google sign in
            val idToken = repository.authorizeGoogleSignIn()
            if (idToken != null) {
                deleteAccount(idToken)
            }
            else {
                // If failed initiate oneTap sign in flow
                // deleteAccount(googleIdToken:) will be called from oneTap result callback
                oneTapSignIn()
                Log.i("AuthViewModel:deleteAccount","OneTapSignIn")
            }
        } else {
            deleteAccount(null)
        }
    }

    fun deleteAccount(googleIdToken: String?) = CoroutineScope(Dispatchers.IO).launch {
        Log.i("AuthViewModel:deleteAccount", "Deleting Account...")
        DataProvider.deleteAccountResponse = Response.Loading
        DataProvider.deleteAccountResponse = repository.deleteUserAccount(googleIdToken)
    }
    /*fun signOut() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.signOutResponse = Response.Loading
        DataProvider.signOutResponse = repository.signOut()
    }*/
}