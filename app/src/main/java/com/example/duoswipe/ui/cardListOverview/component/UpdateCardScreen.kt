package com.example.duoswipe.ui.cardListOverview.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.ui.cardListOverview.CardListOverviewViewModel
import com.example.duoswipe.ui.cardListOverview.UnderlinedTextFieldList

@Composable
fun UpdateCardScreen(
    cardListKey: String,
    cardKey: String,
    firstWord: String,
    secondWord: String,
    viewModel: CardListOverviewViewModel = hiltViewModel(),
) {
    var firstWord by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = firstWord
                )
            )
        }
    )
    var secondWord by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = secondWord
                )
            )
        }
    )
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //.background(color = Color(android.graphics.Color.parseColor("#fe5b52")))
                //.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Save",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        viewModel.updateCard(cardListKey, cardKey, firstWord.text, secondWord.text)
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UnderlinedTextFieldList(
                    value = firstWord.text,
                    onValueChange = { newValue ->
                        //secondWord = newValue
                        firstWord = firstWord.copy(text = newValue)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(0.4f)
                )
                UnderlinedTextFieldList(
                    value = secondWord.text,
                    onValueChange = { newValue ->
                        //secondWord = newValue
                        secondWord = secondWord.copy(text = newValue)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(0.4f)
                )
            }
        }
    }
}