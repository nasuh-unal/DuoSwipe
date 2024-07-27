package com.example.duoswipe.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duoswipe.R

@Composable
fun GoogleButtonComponent() {
    Button(
        onClick = {},
        modifier = Modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(15.dp))
            .width(42.dp)
            .height(42.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    ) {
        Row(
            modifier = Modifier
                //.fillMaxWidth()
                .heightIn(42.dp)
                .widthIn(42.dp),
            verticalAlignment = Alignment . CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreGoogleButtonComponent() {
    GoogleButtonComponent()
}