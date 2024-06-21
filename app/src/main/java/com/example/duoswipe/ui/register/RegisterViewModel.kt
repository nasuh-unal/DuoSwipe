package com.example.duoswipe.ui.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val _registerUiState = MutableStateFlow(RegisterState())
    val registerUiState: StateFlow<RegisterState> = _registerUiState.asStateFlow()

    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _signInState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState(){
        _signInState.update {
            SignInState()
        }
    }
}