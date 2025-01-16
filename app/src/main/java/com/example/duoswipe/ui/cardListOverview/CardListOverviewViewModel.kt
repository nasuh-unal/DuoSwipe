package com.example.duoswipe.ui.cardListOverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.DatabaseRepository
import com.example.duoswipe.data.repository.GetCardListResponse
import com.example.duoswipe.data.repository.SetCardListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListOverviewViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    var getCardListResponse by mutableStateOf<GetCardListResponse>(Response.Loading)

    var updateCardResponse by mutableStateOf<SetCardListResponse>(Response.Loading)

    var deleteCardResponse by mutableStateOf<SetCardListResponse>(Response.Success(null))

    fun getCardsList(cardKey: String) = viewModelScope.launch {
        println(cardKey)
        getCardListResponse = Response.Loading
        getCardListResponse = databaseRepository.getCardListFromRealtimeDatabase(cardKey)

    }

    fun deleteCard(cardListKey: String, cardKey: String) = viewModelScope.launch {
        deleteCardResponse = Response.Loading
        deleteCardResponse = databaseRepository.deleteCardFromRealtimeDatabase(
            cardListKey = cardListKey,
            cardKey = cardKey
        )
    }

    fun updateCard(
        cardListKey: String,
        cardKey: String,
        updatedFirstWord: String,
        updatedSecondWord: String
    ) = viewModelScope.launch {
        updateCardResponse = Response.Loading
        updateCardResponse = databaseRepository.updateCardInRealtimeDatabase(
            cardListKey = cardListKey,
            cardKey = cardKey,
            updatedFirstWord = updatedFirstWord,
            updatedSecondWord = updatedSecondWord
        )
    }
}

/*
var cardLista by mutableStateOf<List<Card>?>(emptyList())
        private set
    fun fetchCardList(cardKey: String) = viewModelScope.launch {

        getCardListResponse = Response.Loading
        val responses = databaseRepository.getCardListFromRealtimeDatabase(cardKey)
        if (responses is Response.Success) {
            val cardListaa = responses.data!!
            cardLista = cardListaa.cards
        }
        println("aaaaa" + cardLista)
    }
 */