package com.example.duoswipe.ui.cardStack.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.cardStack.CardStackScreen
import com.example.duoswipe.ui.cardStack.CardStackViewModel
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator

@Composable
fun CardStackControl(
    cardListKey:String,
    viewModel: CardStackViewModel = hiltViewModel()
){
    LaunchedEffect(Unit) {
        viewModel.getCardListStack(cardListKey)
    }
    when (val getCardListResponse = viewModel.getCardListResponse) {
        is Response.Loading -> AuthLoginProgressIndicator()
        is Response.Success -> {
            val list = getCardListResponse.data?.cards.orEmpty()
            CardStackScreen(
                cardList = list
            )
        }
        is Response.Failure -> getCardListResponse.apply {
            LaunchedEffect(key1 = e) {
                println(e)
            }
        }
    }
}


