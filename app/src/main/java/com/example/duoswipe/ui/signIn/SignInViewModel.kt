package com.example.duoswipe.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        DataProvider.signInResponse = Response.Loading
        DataProvider.signInResponse = repository.firebaseSignInWithEmailAndPassword(email, password)
    }
}