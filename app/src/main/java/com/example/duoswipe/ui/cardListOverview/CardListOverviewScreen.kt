package com.example.duoswipe.ui.cardListOverview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.R
import com.example.duoswipe.data.model.Card
import com.example.duoswipe.data.model.Response
import com.example.duoswipe.ui.component.FlashCardTextField
import com.example.duoswipe.ui.component.MyBottomBar
import com.example.duoswipe.ui.overview.FabState
import com.example.duoswipe.ui.overview.component.AddCardToList
import com.example.duoswipe.ui.overview.component.NewCardListCreate

@Composable
fun CardListOverviewScreen(
    cardKey: String,
    navigateToProfileScreen: () -> Unit,
    navigateToOverviewScreen: () -> Unit,
    navigateToUpdateCardScreen: (cardListKey: String, cardKey: String, firstWord: String, secondWord: String) -> Unit,
    viewModel: CardListOverviewViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getCardsList(cardKey)
        //viewModel.fetchCardList(key)
    }
    val scaffoldState = remember { SnackbarHostState() }
    Scaffold(
        bottomBar = {
            MyBottomBar(navigateToOverviewScreen, navigateToProfileScreen)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                modifier = Modifier.offset(y = 60.dp),
                contentColor = Color.White,
                containerColor = Color(android.graphics.Color.parseColor("#fe5b52"))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                    contentDescription = "Start",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = scaffoldState) },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TitleList()
                SearchBarWord()
            }
            when (val getCardListResponse = viewModel.getCardListResponse) {
                is Response.Loading -> println("yükleniyorr") //AuthLoginProgressIndicator()
                is Response.Success -> {
                    val aa = getCardListResponse.data?.cards
                    CardListOverviewList(
                        cardList = aa,
                        cardListKey = getCardListResponse.data?.key.toString(),
                        navigateToUpdateCardScreen = navigateToUpdateCardScreen
                    )
                    println(aa)
                }

                is Response.Failure -> getCardListResponse.apply {
                    LaunchedEffect(key1 = e) {
                        println(e)
                    }
                }
            }
        }
    }
}

@Composable
fun CardListOverviewList(
    cardList: List<Card>?,
    cardListKey: String,
    viewModel: CardListOverviewViewModel = hiltViewModel(),
    navigateToUpdateCardScreen: (cardListKey: String, cardKey: String, firstWord: String, secondWord: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        cardList?.let {
            items(cardList) { index ->
                var firstWord by rememberSaveable(
                    stateSaver = TextFieldValue.Saver,
                    init = {
                        mutableStateOf(
                            value = TextFieldValue(
                                text = index.firstWord.toString()
                            )
                        )
                    }
                )
                var secondWord by rememberSaveable(
                    stateSaver = TextFieldValue.Saver,
                    init = {
                        mutableStateOf(
                            value = TextFieldValue(
                                text = index.secondWord.toString()
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
                            .padding(start = 22.dp)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            /*UnderlinedTextFieldList(
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
                            )*/
                            Text(
                                text = firstWord.text, // İlk kelimeyi gösteriyoruz
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                            )
                            Text(
                                text = secondWord.text, // İkinci kelimeyi gösteriyoruz
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                modifier = Modifier
                            )
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Edit",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier.clickable {
                                        navigateToUpdateCardScreen(
                                            cardListKey,
                                            index.key.toString(),
                                            index.firstWord.toString(),
                                            index.secondWord.toString()
                                        )
                                    }
                                )
                                Text(
                                    text = "Sil",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier.clickable {
                                        viewModel.deleteCard(cardListKey = cardListKey, cardKey = index.key.toString())
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnderlinedTextFieldList(
    hint: String = "Enter text here",
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text( // Hint metni
                text = hint,
                style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(
                width = 0.dp, // Üst, sol, sağ çizgiyi kaldırmak için
                color = Color.Transparent
            )
            .drawBehind {
                // Alt çizgiyi çizen bir özellik
                val lineOffset = size.height - 7.dp.toPx()
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, lineOffset),
                    end = Offset(size.width, lineOffset),
                    strokeWidth = 2.dp.toPx()
                )
            },
        singleLine = true, // Tek satır
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun TitleList() {
    Text(
        text = "Word List",
        color = Color.Black,
        fontSize = 35.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 18.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWord() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        SearchBar(
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(
                    text = "Search",
                    modifier = Modifier.fillMaxWidth(),
                    //textAlign = androidx.compose.ui.text.style.TextAlign.
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                active = false
                            }
                        },
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp), // Daha ince bir yükseklik
            colors = SearchBarDefaults.colors(
                containerColor = Color.White, // Arka plan rengi
                dividerColor = Color.Transparent, // Alt çizgi rengi
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp) // Köşe yuvarlaması
        ) {

        }
    }
}


/*fun prepareCardList(): List<Card> {
    val cardItemsList = arrayListOf<Card>()
    cardItemsList.add(Card(firstWord = "nasuh", secondWord = "unal"))
    cardItemsList.add(Card(firstWord = "nasuh", secondWord = "unal"))
    cardItemsList.add(Card(firstWord = "nasuh", secondWord = "unal"))
    return cardItemsList
}*/

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCardListOverviewScreen() {
    CardListOverviewScreen(
        navigateToProfileScreen = { *//* Boş bir lambda, preview için gerekli *//* },
        navigateToOverviewScreen = { *//* Boş bir lambda, preview için gerekli *//* }
    )
}*/

