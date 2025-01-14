package com.example.duoswipe.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun FlashCardTextField(
    frontText: TextFieldValue,
    backText: TextFieldValue,
    isFlipped: Boolean,
    onValueChange: (newValue: TextFieldValue) -> Unit,
) {
    //var isFlipped=isFlipped
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
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)// Kartın genişliğini ekranın %80'i ile sınırlar
                .aspectRatio(1.5f)// Kartın en-boy oranını ayarlar
                .clip(RoundedCornerShape(16.dp))// Kartın köşelerini yuvarlatır
                //.clickable { isFlipped = !isFlipped }// Kart tıklandığında yönünü değiştirir
                .graphicsLayer {
                    rotationY = rotation.value// Kartın yatay eksende dönüşünü sağlar
                    cameraDistance = 12 * density// 3D efekt için kamera mesafesini ayarlar
                }
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (frontVisible) {
                UnderlinedTextField(
                    value = frontText.text,
                    onValueChange = { newValue -> onValueChange(TextFieldValue(newValue)) },
                    isFlipped = false
                )
            } else if (backVisible) {
                UnderlinedTextField(
                    value = backText.text,
                    onValueChange = { newValue -> onValueChange(TextFieldValue(newValue)) },
                    isFlipped = true
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnderlinedTextField(
    hint: String = "Enter text here",
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isFlipped: Boolean
) {
    val focusRequester = remember { FocusRequester() }

    // LaunchedEffect ile otomatik odaklama
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val updatedModifier = if (isFlipped) {
        modifier.graphicsLayer {
            rotationY = 180f
        }
    } else {
        modifier
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text( // Hint metni
                text = hint,
                style = TextStyle(fontSize = 16.sp, color = Color.Gray)
            )
        },
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        modifier = updatedModifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester)
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        )
    )
}


@Preview(showBackground = true)
@Composable
fun FlashCardTextFieldPreview() {
    // Varsayılan değerler
    val frontText = remember { mutableStateOf(TextFieldValue("Front Text")) }
    val backText = remember { mutableStateOf(TextFieldValue("Back Text")) }
    val isFlipped by remember { mutableStateOf(false) }

    // Preview UI
    FlashCardTextField(
        frontText = frontText.value,
        backText = backText.value,
        isFlipped = isFlipped,
        onValueChange = { newValue ->
            if (isFlipped) {
                backText.value = newValue
            } else {
                frontText.value = newValue
            }
        }
    )
}

/*OutlinedTextField(
label = {
    Text(
        "Back Text",
        style = TextStyle(fontSize = 14.sp, color = Color.Black)
    )
},
value = backText,
onValueChange = { newValue -> onValueChange(newValue) },
textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
modifier = Modifier
.padding(16.dp)
.graphicsLayer {
    rotationY = 180f
}
)*/

/*OutlinedTextField(
                    label = { Text("Front Text", style = TextStyle(fontSize = 12.sp, color = Color.Black)) },
                    value = frontText,
                    onValueChange = { newValue -> onValueChange(newValue) },
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    modifier = Modifier.padding(16.dp)
                )*/


/*@Composable
fun RoundedOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, style = TextStyle(color = Color.Gray, fontSize = 14.sp)) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        ),
        colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF64B5F6), // Highlighted Blue
            unfocusedBorderColor = Color.LightGray,
            textColor = Color.Black
        )
    )
}*/

