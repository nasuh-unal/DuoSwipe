package com.example.duoswipe.ui.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.Card
import com.example.duoswipe.data.model.CardList
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.GetCardListResponse
import com.example.duoswipe.data.repository.DatabaseRepository
import com.example.duoswipe.data.repository.GetCardListsResponse
import com.example.duoswipe.data.repository.SetCardListResponse
import com.example.duoswipe.data.repository.SetCardResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    var getCardListsResponse by mutableStateOf<GetCardListsResponse>(Response.Loading)
        private set

    var cardListsa by mutableStateOf<List<CardList>>(emptyList())
        private set

    var setCardResponse by mutableStateOf<SetCardResponse>(Response.Success(null))
        private set
    var setCardListAndSetListResponse by mutableStateOf<SetCardListResponse>(Response.Success(null))
        private set

    var deleteCardListResponse by mutableStateOf<SetCardListResponse>(Response.Success(null))

    init {
        getCardLists()
        fetchCardLists()
    }

    fun deleteCardsKeysEach(cardKeysToDelete: List<String>){
        viewModelScope.launch {
            cardKeysToDelete.forEach { key ->
                deleteCardListFromDatabase(key)
            }
        }
    }

    private fun deleteCardListFromDatabase(cardListKey:String)=viewModelScope.launch{
        deleteCardListResponse=Response.Loading
        deleteCardListResponse=databaseRepository.deleteCardList(cardListKey)

    }

    fun fetchCardLists() = viewModelScope.launch {
        getCardListsResponse = Response.Loading
        val response = databaseRepository.getCardListsFromRealtimeDatabase()
        if (response is Response.Success) {
            cardListsa = response.data!!.toList()
        }
    }
    fun getCardLists() = viewModelScope.launch {//
        getCardListsResponse = Response.Loading
        getCardListsResponse = databaseRepository.getCardListsFromRealtimeDatabase()
    }

    fun setCardAndSetList(cardListName: String, card: Card) = viewModelScope.launch {
        setCardListAndSetListResponse = Response.Loading
        setCardListAndSetListResponse =
            databaseRepository.setCardAndSetListToRealTimeDatabase(cardListName, card)
    }

    fun setCard(cardListKey: String, card: Card) = viewModelScope.launch {
        setCardResponse = Response.Loading
        setCardResponse = databaseRepository.setCardToRealtimeDatabase(cardListKey, card)
    }
}