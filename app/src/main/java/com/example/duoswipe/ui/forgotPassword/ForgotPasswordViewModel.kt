package com.example.duoswipe.ui.forgotPassword
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: AuthRepository) :
    ViewModel() {
    fun sendPasswordEmail(email: String) = viewModelScope.launch {
        DataProvider.sendPasswordResetEmailResponse = Response.Loading
        DataProvider.sendPasswordResetEmailResponse = repository.sendPasswordResetEmail(email)
    }
}