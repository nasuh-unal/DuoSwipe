package com.example.duoswipe.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.AuthRepository
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository,
    val oneTapClient: SignInClient
) : ViewModel() {


    fun deleteAccount(googleIdToken: String?) = CoroutineScope(Dispatchers.IO).launch {
        Log.i("AuthViewModel:deleteAccount", "Deleting Account...")
        DataProvider.deleteAccountResponse = Response.Loading
        DataProvider.deleteAccountResponse = repository.deleteUserAccount(googleIdToken)
    }
    fun signOut() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.signOutResponse = Response.Loading
        DataProvider.signOutResponse = repository.signOut()
    }

    fun checkNeedsReAuth() = CoroutineScope(Dispatchers.IO).launch {
        if (repository.checkNeedsReAuth()) {
            val idToken = repository.authorizeGoogleSignIn()
            if (idToken != null) {
                deleteAccount(null)
            }
        }
    }
}