package com.example.duoswipe.ui.overview.component
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.component.AuthLoginProgressIndicator
import com.example.duoswipe.ui.overview.OverviewViewModel

@Composable
fun Overview(
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    when (val setCarListResponse = viewModel.setCardListAndSetListResponse) {
        is Response.Loading -> Unit//AuthLoginProgressIndicator()
        is Response.Success -> viewModel.getCardLists()//liste ve card oluşturduktan sonra ekranı yenileme işlemi
        is Response.Failure -> setCarListResponse.apply {
            LaunchedEffect(e) {
                print("nasuhunal"+e)
            }
        }
    }
}