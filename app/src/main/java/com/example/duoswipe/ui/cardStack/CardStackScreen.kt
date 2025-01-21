package com.example.duoswipe.ui.cardStack
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ExperimentalMotionApi
import com.example.duoswipe.data.model.Card
import kotlinx.coroutines.launch

enum class SwipeDirectionn { LEFT, RIGHT }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMotionApi::class)
@Composable
fun CardStackScreen(cardList: List<Card>) {
    var currentCardIndex by remember { mutableIntStateOf(0) }
    var correctCount by remember { mutableIntStateOf(0) }
    var wrongCount by remember { mutableIntStateOf(0) }
    val isFinished = currentCardIndex >= cardList.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Quiz Mode") },
                navigationIcon = {
                    Button(onClick = { /* Finish quiz logic */ }) {
                        Text("Finish")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isFinished) {
                QuizResult(correctCount, wrongCount)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                ) {
                    // Skorların ekranda gösterildiği kısım
                    Text(
                        text = "Correct: $correctCount | Wrong: $wrongCount", // Skorları dinamik göster
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp,
                        color = Color.Yellow
                    )
                    // Kartların yığıldığı CardStack bileşeninin çağrımı
                    CardStack(
                        cardList = cardList,
                        currentIndex = currentCardIndex,
                        onSwipe = { direction ->
                            if (direction == SwipeDirectionn.RIGHT) correctCount++ else wrongCount++
                            currentCardIndex++
                        }
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun CardStack(
    cardList: List<Card>,
    currentIndex: Int,
    onSwipe: (SwipeDirectionn) -> Unit
) {
    val visibleCards = cardList.subList(currentIndex, cardList.size).take(3)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        visibleCards.reversed().forEachIndexed { index, card ->
            val isTopCard = index == visibleCards.size - 1
            val offsetX = remember { Animatable(0f) }
            val offsetY = remember { Animatable(0f) }
            var flipped by remember { mutableStateOf(false) }
            val scale = 1f - (0.05f * index)

            val rotationY by animateFloatAsState(
                targetValue = if (flipped) 180f else 0f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )

            val coroutineScope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1.5f)
                    .scale(scale)
                    .offset(x = offsetX.value.dp, y = offsetY.value.dp)
                    .clip(RoundedCornerShape(16.dp))
                    //.border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                    .background(if (rotationY <= 90f) Color.Yellow else Color.LightGray)
                    .graphicsLayer (
                        rotationY=rotationY,
                        cameraDistance = 12f * LocalDensity.current.density
                    )
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    if (abs(offsetX.value) > 300) {
                                        onSwipe(if (offsetX.value > 0) SwipeDirectionn.RIGHT else SwipeDirectionn.LEFT)
                                        offsetX.snapTo(0f)
                                        offsetY.snapTo(0f)
                                    } else {
                                        offsetX.animateTo(0f, spring())
                                        offsetY.animateTo(0f, spring())
                                    }
                                }
                            },
                            onDrag = { _, dragAmount ->
                                coroutineScope.launch {
                                    if (isTopCard) {
                                        offsetX.snapTo(offsetX.value + dragAmount.x)
                                        offsetY.snapTo(offsetY.value + dragAmount.y)
                                    }
                                }
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { flipped = !flipped })
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
                        /*.graphicsLayer (
                            rotationY=rotationY,
                            cameraDistance = 12f * LocalDensity.current.density
                        )
                        .background(if (rotationY <= 90f) Color.Yellow else Color.LightGray),*/
                    contentAlignment = Alignment.Center
                ) {
                    if (rotationY <= 90f) {
                        Text(
                            text = card.secondWord.toString(),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(32.dp)
                        )
                    } else {
                        Text(
                            text = card.firstWord.toString(),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizResult(correctCount: Int, wrongCount: Int) {
    // Quiz bittiğinde sonuçları gösteren bileşen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Quiz Finished!", fontSize = 32.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Correct: $correctCount", fontSize = 24.sp, color = Color.Green)
        Text(text = "Wrong: $wrongCount", fontSize = 24.sp, color = Color.Red)
    }
}

@Preview
@Composable
fun preCardStackScreen() {
    val cardlisss = listOf(
        Card(firstWord = "merhaba", secondWord = "hello"),
        Card(firstWord = "kalem", secondWord = "pencil"),
        Card(firstWord = "okul", secondWord = "school"),
        Card(firstWord = "ev", secondWord = "house"),
        Card(firstWord = "bilgisayar", secondWord = "computer"),
        Card(firstWord = "araba", secondWord = "car"),
        Card(firstWord = "kitap", secondWord = "book")

    )
    CardStackScreen(cardList = cardlisss)
}