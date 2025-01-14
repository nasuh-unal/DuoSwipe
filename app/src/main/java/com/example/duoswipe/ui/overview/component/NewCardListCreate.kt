package com.example.duoswipe.ui.overview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun NewCardListCreate(
    listName: TextFieldValue,
    onValueChange: (newValue: TextFieldValue) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()//sayesinde ekranın ortasına geldi
            .padding(28.dp)
            .wrapContentSize()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Yellow),
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
                text = "New Collections",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        //Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.padding(18.dp),
            label = { Text("Enter your text") },
            value = listName,
            onValueChange = { newValue -> onValueChange(newValue) },
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreNewCardListCreate() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    NewCardListCreate(listName = text,
        onValueChange = { newValue -> text = newValue })
}