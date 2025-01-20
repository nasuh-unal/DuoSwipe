package com.example.duoswipe.ui.overview

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.R
import com.example.duoswipe.core.Utils
import com.example.duoswipe.data.model.CardList
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.data.model.FabState
import com.example.duoswipe.data.model.OverviewDataProvider.fabState
import com.example.duoswipe.data.repository.CardLists
import com.example.duoswipe.ui.component.FlashCardTextField
import com.example.duoswipe.ui.component.MyBottomBar
import com.example.duoswipe.ui.overview.component.AddCardToList
import com.example.duoswipe.ui.overview.component.NewCardListCreate
import com.example.duoswipe.ui.overview.component.Overview
import com.example.duoswipe.ui.overview.component.VerticalCardListCheck

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

/*enum class FabState {
    BLANK, ADD, TURN, NEXT, SAVE, FINISH
}*/


@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit,
    navigateToOverviewScreen: () -> Unit,
    navigateToCardListOverviewScreen: (cardKey: String) -> Unit
) {
    val scaffoldState = remember { SnackbarHostState() }
    //var fabState by remember { mutableStateOf(FabState.BLANK) }
    val context = LocalContext.current
    var listName by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )

    var firstWord by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    var secondWord by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    Scaffold(
        bottomBar = {
            MyBottomBar(navigateToOverviewScreen, navigateToProfileScreen)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    fabState = when (fabState) {
                        FabState.BLANK -> FabState.ADD
                        FabState.ADD -> FabState.TURN
                        FabState.TURN -> FabState.NEXT
                        FabState.NEXT -> FabState.SAVE
                        FabState.SAVE -> FabState.FINISH
                        else -> FabState.BLANK
                    }
                    if (fabState == FabState.FINISH) {
                        viewModel.setCardAndSetList(
                            cardListName = listName.text,
                            card = com.example.duoswipe.data.model.Card(
                                firstWord = firstWord.text,
                                secondWord = secondWord.text
                            )
                        )
                        //viewModel.getCardLists()
                    }
                },
                modifier = Modifier.offset(y = 60.dp),
                contentColor = Color.White,
                containerColor = Color(android.graphics.Color.parseColor("#fe5b52"))
            ) {
                Icon(
                    painter = when (fabState) {
                        FabState.BLANK -> painterResource(id = R.drawable.baseline_add_24)
                        FabState.ADD -> painterResource(id = R.drawable.baseline_360_24)
                        FabState.TURN -> painterResource(id = R.drawable.baseline_navigate_next_24)
                        FabState.NEXT -> painterResource(id = R.drawable.baseline_create_new_folder_24)
                        FabState.SAVE -> painterResource(id = R.drawable.baseline_save_24)
                        FabState.FINISH -> painterResource(id = R.drawable.baseline_add_24)
                    },
                    contentDescription = "add",
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
                .padding(paddingValues)
                //.clickable { if (isBlurred==true) isBlurred=false }
                .then(
                    when (fabState) {
                        FabState.ADD -> Modifier.blur(10.dp)
                        FabState.TURN -> Modifier.blur(10.dp)
                        FabState.SAVE -> Modifier.blur(10.dp)
                        FabState.NEXT -> Modifier.blur(10.dp)
                        else -> Modifier
                    }
                ) // Column'a da blur uyguluyoruz
        ) {
            VerticalCardListCheck(
                navigateToCardListOverviewScreen = navigateToCardListOverviewScreen,
                showErrorMessage = { errorMessage ->
                    Utils.showMessage(context, errorMessage)
                }
            )
        }
        //AddCard(isDialogVisible = isBlurred)
        when (fabState) {
            FabState.ADD -> FlashCardTextField(
                frontText = firstWord,
                backText = secondWord,
                false,
                onValueChange = { firstWord = it })

            FabState.TURN -> FlashCardTextField(
                frontText = firstWord,
                backText = secondWord,
                true,
                onValueChange = { secondWord = it })

            FabState.NEXT -> AddCardToList(
                frontText = firstWord,
                backText = secondWord
            )

            FabState.SAVE -> NewCardListCreate(
                listName = listName,
                onValueChange = { listName = it })

            FabState.BLANK -> {}
            FabState.FINISH -> {}
        }
    }
    Overview()
}

@Composable
fun VerticalCardList(
    navigateToCardListOverviewScreen: (cardKey: String) -> Unit,
    cardLists: CardLists?,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    var isDeleteMode by remember { mutableStateOf(false) }
    val selectedCardKeys = remember { mutableStateListOf<String>() }
    LazyColumn(
        contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp),
        //modifier=Modifier.background(color = Color(android.graphics.Color.parseColor("#37c9bb")))
    ) {
        item {
            NameProfile()
            Title()
            Button(
                onClick = {
                    if (isDeleteMode) {
                        // Silme işlemi
                        viewModel.deleteCardsKeysEach(selectedCardKeys)
                        viewModel.getCardLists()
                        viewModel.fetchCardLists()
                        selectedCardKeys.clear()
                    }
                    isDeleteMode = !isDeleteMode
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (isDeleteMode) "Seçilenleri Sil" else "Sil Modu")
            }
        }
        cardLists?.let {
            items(cardLists.chunked(2)) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowIndex.forEach { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .aspectRatio(1.1f),
                            onClick = {
                                index.key?.let { cardKey ->
                                    navigateToCardListOverviewScreen(cardKey)
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color(android.graphics.Color.parseColor("#37c9bb"))),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (isDeleteMode) {
                                    Checkbox(
                                        checked = selectedCardKeys.contains(index.key),
                                        onCheckedChange = { isSelected ->
                                            if (isSelected) {
                                                index.key?.let { selectedCardKeys.add(it) }
                                            } else {
                                                index.key?.let { selectedCardKeys.remove(it) }
                                            }
                                        }
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_view_headline_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(65.dp)
                                        .width(65.dp)
                                )
                                val listName = index.listName ?: "NO_VALUE"
                                Text(
                                    text = listName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                    if (rowIndex.size < 2) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.5f) // Boş alanı dengeler
                                .aspectRatio(1.5f)
                        )
                    }
                }
            }
        }
    }
}

/*@Composable
fun deleteButton() {
    Button(
        onClick = {
            if (isDeleteMode) {
                // Silme işlemi
                onDeleteSelected(selectedCards.toList())
                selectedCards.clear()
            }
            isDeleteMode = !isDeleteMode
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(if (isDeleteMode) "Seçilenleri Sil" else "Sil Modu")
    }
}*/

@Composable
fun Title() {
    Text(
        "What would you like to\nlearn today?",
        color = Color.Black,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 24.dp)
    )
}

@Composable
fun NameProfile() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp, start = 24.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.baseline_person_24),
            contentDescription = null,
            modifier = Modifier
                .width(55.dp)
                .height(55.dp)
                .clickable { })
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 8.dp)
                .height(30.dp)
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text(
                text = DataProvider.user?.displayName.toString(),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}