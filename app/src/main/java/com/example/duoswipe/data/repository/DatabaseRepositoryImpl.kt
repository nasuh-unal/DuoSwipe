package com.example.duoswipe.data.repository
import com.example.duoswipe.core.Constants.CARDLISTS
import com.example.duoswipe.data.model.Card
import com.example.duoswipe.data.model.CardList
import com.example.duoswipe.data.model.Response
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val db: DatabaseReference
) : DatabaseRepository {
    override suspend fun getCardListFromRealtimeDatabase(cardKey: String): GetCardListResponse =
        try {
            val snapshot = db.child(CARDLISTS).child(cardKey).get().await()
            val cardList = snapshot.toCardList()
            if (cardList != null) {
                Response.Success(cardList)
            } else {
                Response.Failure(Exception("CardList not found for key: $cardKey"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }

    //metot name düzelt
    override suspend fun getCardListsFromRealtimeDatabase(): GetCardListsResponse = try {
        val cardLists = mutableListOf<CardList>()
        db.child(CARDLISTS).get().await().children.forEach { snapshot ->
            val cardList = snapshot.toCardList()
            cardLists.add(cardList)
        }
        Response.Success(cardLists)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    //realtime kaldır ismi
    override suspend fun setCardToRealtimeDatabase(
        cardListKey: String,
        card: Card
    ): SetCardResponse = try {
        val cardKey = db.child(CARDLISTS).child(cardListKey).child("cards").push().key
        if (cardKey != null) {
            val cardRef = db.child(CARDLISTS).child(cardListKey).child("cards").child(cardKey)
            card.key = cardKey
            cardRef.setValue(card).await()
            Response.Success(true)
        } else {
            Response.Failure(Exception("Failed to generate card key"))
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun setCardAndSetListToRealTimeDatabase(
        cardListName: String,
        card: Card
    ): SetCardListResponse = try {
        val listKey = db.child(CARDLISTS).push().key
        if (listKey != null) {
            val cardKey = db.child(CARDLISTS).child(listKey).child("cards").push().key
            card.key = cardKey

            val newCardList = CardList(
                key = listKey,
                listName = cardListName,
                cards = if (cardKey != null) listOf(card.copy(key = cardKey)) else emptyList()
            )
            db.child(CARDLISTS).child(listKey).setValue(newCardList).await()
            Response.Success(true) // Başarılıysa oluşturulan listenin key'ini döndürürüz.
        }
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
    override suspend fun deleteCardFromRealtimeDatabase(
        cardListKey: String,
        cardKey: String
    ): Response<Boolean> = try {
        // Silinmek istenen kartın referansını alıyoruz
        val cardRef = db.child(CARDLISTS)
            .child(cardListKey)
            .child("cards")
            .child(cardKey)

        // Referansı siliyoruz
        cardRef.removeValue().await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun updateCardInRealtimeDatabase(
        cardListKey: String,
        cardKey: String,
        updatedFirstWord: String,
        updatedSecondWord: String
    ): Response<Boolean> = try {
        val cardRef = db.child(CARDLISTS).child(cardListKey).child("cards").child(cardKey)
        val updates = mapOf<String, Any?>(
            "firstWord" to updatedFirstWord,
            "secondWord" to updatedSecondWord
        )
        cardRef.updateChildren(updates).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}


fun DataSnapshot.toCardList(): CardList {
    val listName = child("listName").getValue(String::class.java) ?: ""
    val cards = child("cards").children.mapNotNull { cardSnapshot ->
        cardSnapshot.getValue(Card::class.java)
    }
    return CardList(
        key = key ?: "",
        listName = listName,
        cards = cards
    )
}


/*override suspend fun getCardListFromRealtimeDatabase(cardKey: String): GetCardListResponse = try {
    val cardListKeyRef = db.child(CARDLISTS).child(cardKey)
    val cardList = cardListKeyRef.get().await().getValue(CardList::class.java)
    Response.Success(cardList)
} catch (e: Exception) {
    Response.Failure(e)
}*/

/*fun DataSnapshot.toCardList(): CardList {
    val listName = child("listName").getValue(String::class.java) ?: ""
    val cards = child("cards").children.mapNotNull { cardSnapshot ->
        val card = cardSnapshot.getValue(Card::class.java)
        card?.key = cardSnapshot.key // Kartların key'lerini de set ediyoruz.
        card
    }
    return CardList(
        key = key ?: "",
        listName = listName,
        cards = cards
    )
}*/
