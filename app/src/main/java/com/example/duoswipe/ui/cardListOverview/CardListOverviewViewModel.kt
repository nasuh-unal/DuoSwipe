package com.example.duoswipe.ui.cardListOverview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duoswipe.data.model.Card
import com.example.duoswipe.data.model.CardList
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.data.repository.DatabaseRepository
import com.example.duoswipe.data.repository.GetCardListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CardListOverviewViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    var cardLista by mutableStateOf<List<Card>?>(emptyList())
        private set
    var getCardListResponse by mutableStateOf<GetCardListResponse>(Response.Loading)

     fun fetchCardList(cardKey: String) = viewModelScope.launch {

        getCardListResponse = Response.Loading
        val responses = databaseRepository.getCardListFromRealtimeDatabase(cardKey)
        if (responses is Response.Success) {
            val cardListaa = responses.data!!
            cardLista = cardListaa.cards
        }
         println("aaaaa"+cardLista)
    }

    fun getCardsList(cardKey: String) = viewModelScope.launch {
        println(cardKey)
        getCardListResponse = Response.Loading
        getCardListResponse = databaseRepository.getCardListFromRealtimeDatabase(cardKey)

    }
}