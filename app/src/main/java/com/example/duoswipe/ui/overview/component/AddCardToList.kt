package com.example.duoswipe.ui.overview.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.R
import com.example.duoswipe.data.model.AuthState
import com.example.duoswipe.data.model.Card
import com.example.duoswipe.data.model.FabState
import com.example.duoswipe.data.model.OverviewDataProvider.fabState
import com.example.duoswipe.ui.overview.OverviewViewModel

@Composable
fun AddCardToList(
    frontText: TextFieldValue,
    backText: TextFieldValue,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val response = viewModel.cardListsa
    Column(
        modifier = Modifier
            .fillMaxSize()//sayesinde ekranın ortasına geldi
            .padding(28.dp)
            .wrapContentSize()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(android.graphics.Color.parseColor("#fe5b52")))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Collections",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(response) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_view_headline_24),
                        contentDescription = null,
                    )
                    Text(
                        text = index.listName.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp)
                            .fillMaxSize(),
                        fontSize = 20.sp,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                index.key?.let { safeKey ->
                                    viewModel.setCard(
                                        safeKey,
                                        Card(firstWord = frontText.text, secondWord = backText.text)
                                    )
                                    fabState =
                                        FabState.BLANK //Kartı bir listeye kaydettikten sonra blank ekrana gitmesini sağlıyorum
                                } ?: Log.e("Error", "index.key is null!")
                            }
                    )
                }
            }
        }
    }
}


