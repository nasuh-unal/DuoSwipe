package com.example.duoswipe.ui.cardStack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.DatabaseRepository
import com.example.duoswipe.data.repository.GetCardListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardStackViewModel @Inject() constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    var getCardListResponse by mutableStateOf<GetCardListResponse>(Response.Loading)

    fun getCardListStack(cardListKey:String)=viewModelScope.launch {
        getCardListResponse=Response.Loading
        getCardListResponse=databaseRepository.getCardListFromRealtimeDatabase(cardListKey)
    }
}