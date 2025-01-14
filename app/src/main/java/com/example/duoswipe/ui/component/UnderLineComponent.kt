package com.example.duoswipe.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duoswipe.R

@Composable
fun UnderLineText(value: String, navigateToForgotPasswordScreen:()->Unit) {
    Text(
        text = value,
        modifier = Modifier
            .clickable { navigateToForgotPasswordScreen() }
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color= colorResource(id = R.color.teal_700),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}
/*
@Preview
@Composable
fun preUnderLineText(){
    UnderLineText(value = stringResource(id = R.string.forgot_password))
}*/
