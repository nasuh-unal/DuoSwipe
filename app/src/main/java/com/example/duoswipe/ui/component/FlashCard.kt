package com.example.duoswipe.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun FlashCard(frontText: String, backText: String,isFlipped:Boolean) {
    // Kartın hangi yüzünün görüneceğini kontrol eden değişken
    //var isFlipped by remember { mutableStateOf(false) }
    // Kartın dönüş derecesini animasyonla kontrol eden değişken
    val rotation = remember { Animatable(0f) }

    // isFlipped değişkeni değiştiğinde animasyon başlatılır
    LaunchedEffect(isFlipped) {
        val targetRotation = if (isFlipped) 180f else 0f
        rotation.animateTo(
            targetRotation,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, // Daha yumuşak bir dönüş için yay animasyonu kullanılıyor
                stiffness = Spring.StiffnessVeryLow // Düşük sertlik değeri ile animasyon daha akıcı olur
            )
        )
    }

    // Kartın ön yüzünün görünüp görünmediğini kontrol eder
    val frontVisible = rotation.value <= 90f
    // Kartın arka yüzünün görünüp görünmediğini kontrol eder
    val backVisible = rotation.value > 90f

    Box(
        modifier = Modifier
            .fillMaxSize() // Tüm ekranı kaplar
            .padding(16.dp), // Ekran kenarlarından boşluk bırakır
        contentAlignment = Alignment.Center // Box içeriğini ekranın ortasına hizalar
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)// Kartın genişliğini ekranın %80'i ile sınırlar
                .aspectRatio(1.5f)// Kartın en-boy oranını ayarlar
                .clip(RoundedCornerShape(16.dp))// Kartın köşelerini yuvarlatır
                //.clickable { isFlipped = !isFlipped }// Kart tıklandığında yönünü değiştirir
                .graphicsLayer {
                    rotationY = rotation.value// Kartın yatay eksende dönüşünü sağlar
                    cameraDistance = 12 * density// 3D efekt için kamera mesafesini ayarlar
                }
                .background(
                    if (frontVisible) Brush.verticalGradient(// Ön yüz gradyan renkleri
                        colors = listOf(
                            Color(0xFF3A1C71),
                            Color(0xFFD76D77),
                            Color(0xFFFFAF7B)
                        )
                    )// Arka yüz gradyan renkleri
                    else Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF00416A),
                            Color(0xFFE4E5E6)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (frontVisible) {
                Text(
                    text = frontText,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            } else if (backVisible) {
                Text(
                    text = backText,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer {
                            rotationY = 180f
                        }
                )
            }
        }
    }


}

@Preview(showSystemUi = true)
@Composable
fun PreFlashCard() {
    FlashCard(frontText = "hello", backText = "merhaba",true)
}