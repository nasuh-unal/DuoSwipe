package com.example.duoswipe.ui.cardListOverview.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.Card
import com.example.duoswipe.data.model.CardList
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.cardListOverview.CardListOverviewScreen
import com.example.duoswipe.ui.cardListOverview.CardListOverviewViewModel
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator
import com.example.duoswipe.ui.overview.OverviewViewModel

/*
@Composable
fun CardListOverviewControl(
    cardKey: String,
    navigateToProfileScreen: () -> Unit,
    navigateToOverviewScreen: () -> Unit,
    viewModel: CardListOverviewViewModel = hiltViewModel()
) {
    when (val getCardListResponse = viewModel.getCardListResponse) {
        is Response.Loading -> println("yükleniyorr") //AuthLoginProgressIndicator()
        is Response.Success -> {
            CardListOverviewScreen(
                cardKey = cardKey,
                navigateToProfileScreen =  navigateToProfileScreen,
                navigateToOverviewScreen = navigateToOverviewScreen
            )
        }

        is Response.Failure -> getCardListResponse.apply {
            LaunchedEffect(key1 = e) {
                println(e)
            }
        }
    }
}*/
