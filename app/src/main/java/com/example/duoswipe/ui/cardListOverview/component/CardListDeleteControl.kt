package com.example.duoswipe.ui.cardListOverview.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.cardListOverview.CardListOverviewViewModel

@Composable
fun CardListDeleteControl(
    viewModel: CardListOverviewViewModel = hiltViewModel()
) {
    when (val getCardListResponse = viewModel.deleteCardResponse) {
        is Response.Loading -> println("deleteyükleniyorr") //AuthLoginProgressIndicator()
        is Response.Success -> Unit
        is Response.Failure -> getCardListResponse.apply {
            LaunchedEffect(key1 = e) {
                println(e)
            }
        }
    }
}