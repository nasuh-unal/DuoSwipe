package com.example.duoswipe.data.repository
import com.example.duoswipe.data.model.Card
import com.example.duoswipe.data.model.CardList
import com.example.duoswipe.data.model.Response

typealias CardLists =List<CardList>
typealias GetCardListsResponse = Response<CardLists>
typealias GetCardListResponse = Response<CardList>
typealias SetCardListResponse = Response<Boolean>
typealias SetCardResponse = Response<Boolean>

interface DatabaseRepository {
    suspend fun getCardListFromRealtimeDatabase(cardKey:String):GetCardListResponse
    suspend fun getCardListsFromRealtimeDatabase():GetCardListsResponse
    suspend fun setCardToRealtimeDatabase(cardListKey: String, card: Card):SetCardResponse
    suspend fun setCardAndSetListToRealTimeDatabase(cardListName:String,card: Card):SetCardListResponse
}