package com.example.duoswipe.ui.overview.component
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.CardList
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator
import com.example.duoswipe.ui.overview.OverviewViewModel
import com.example.duoswipe.ui.overview.VerticalCardList

@Composable
fun VerticalCardListCheck(
    showErrorMessage: (errorMessage: String?) -> Unit,
    navigateToCardListOverviewScreen: (cardKey:String) -> Unit,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    when (val cardListsResponse = viewModel.getCardListsResponse) {
        is Response.Loading -> AuthLoginProgressIndicator()
        is Response.Success -> {
            val cardLists: List<CardList> = cardListsResponse.data ?: emptyList()
            VerticalCardList(
                navigateToCardListOverviewScreen = navigateToCardListOverviewScreen,
                cardLists
            )
        }
        is Response.Failure -> cardListsResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}