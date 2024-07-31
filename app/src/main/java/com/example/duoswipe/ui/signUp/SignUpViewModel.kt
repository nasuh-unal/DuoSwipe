package com.example.duoswipe.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        DataProvider.signUpResponse = Response.Loading
        DataProvider.signUpResponse = repository.firebaseSignUpWithEmailAndPassword(email, password)
    }
    fun sendEmailVerification() = viewModelScope.launch {
        DataProvider.sendEmailVerificationResponse = Response.Loading
        DataProvider.sendEmailVerificationResponse = repository.sendEmailVerification()
    }
}