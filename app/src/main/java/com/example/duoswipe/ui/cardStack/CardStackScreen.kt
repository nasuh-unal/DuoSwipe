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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.data.model.Card
import kotlinx.coroutines.launch

enum class SwipeDirection { LEFT, RIGHT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardStackScreen(
    cardList: List<Card>
) {
    var currentCardIndex by remember { mutableIntStateOf(0) }
    var correctCount by remember { mutableIntStateOf(0) }
    var wrongCount by remember { mutableIntStateOf(0) }
    val isFinished = currentCardIndex >= cardList.size



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quiz Mode",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        },
        floatingActionButton = {
            if (!isFinished) {
                FloatingActionButton(
                    onClick = { currentCardIndex = cardList.size },
                    containerColor = Color(0xFFFF5722)
                ) {
                    Text("Finish", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFBBDEFB),
                            Color(0xFFE3F2FD)
                        )
                    )
                )
        ) {
            if (isFinished) {
                QuizResult(correctCount, wrongCount)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Correct: $correctCount | Wrong: $wrongCount",
                        fontSize = 24.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CardStack(
                        cardList = cardList,
                        currentIndex = currentCardIndex,
                        onSwipe = { direction ->
                            if (direction == SwipeDirection.RIGHT) correctCount++ else wrongCount++
                            currentCardIndex++
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun CardStack(
    cardList: List<Card>,
    currentIndex: Int,
    onSwipe: (SwipeDirection) -> Unit
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
            val verticalOffset = (index * 20).dp

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
                    .offset(
                        x = if (rotationY % 360 > 90f && rotationY % 360 < 270f) -offsetX.value.dp else offsetX.value.dp,
                        y = offsetY.value.dp + verticalOffset
                    ) // Ters yönde hareketi düzelt
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFFD54F),
                                Color(0xFFFFB300)
                            )
                        )
                    )
                    .graphicsLayer(
                        rotationY = rotationY,
                        cameraDistance = 12f * LocalDensity.current.density
                    )
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    if (abs(offsetX.value) > 300) {
                                        val effectiveDirection =
                                            if (rotationY % 360 > 90f && rotationY % 360 < 270f) {
                                                if (offsetX.value > 0) SwipeDirection.LEFT else SwipeDirection.RIGHT
                                            } else {
                                                if (offsetX.value > 0) SwipeDirection.RIGHT else SwipeDirection.LEFT
                                            }
                                        onSwipe(effectiveDirection)
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
                    modifier = Modifier
                        .fillMaxSize()
                        .border(4.dp, Color.Black, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (rotationY % 360 <= 90f || rotationY % 360 >= 270f) {
                        // Ön yüz
                        Text(
                            text = card.secondWord.toString(),
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(32.dp),
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold

                        )
                    } else {
                        // Arka yüz
                        Text(
                            text = card.firstWord.toString(),
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(32.dp)
                                .graphicsLayer(scaleX = -1f),
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun QuizResult(correctCount: Int, wrongCount: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quiz Finished!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Correct: $correctCount",
            fontSize = 30.sp,
            color = Color(0xFF4CAF50)
        )
        Text(
            text = "Wrong: $wrongCount",
            fontSize = 30.sp,
            color = Color(0xFFF44336)
        )
    }
}

@Preview
@Composable
fun PreCardStackScreen() {
    val cardList = listOf(
        Card(firstWord = "Merhaba", secondWord = "Hello"),
        Card(firstWord = "Kalem", secondWord = "Pencil"),
        Card(firstWord = "Okul", secondWord = "School"),
        Card(firstWord = "Ev", secondWord = "House"),
        Card(firstWord = "Bilgisayar", secondWord = "Computer"),
        Card(firstWord = "Araba", secondWord = "Car"),
        Card(firstWord = "Kitap", secondWord = "Book")
    )
    CardStackScreen(cardList = cardList)
}
