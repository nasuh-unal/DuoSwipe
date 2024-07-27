package com.example.duoswipe.ui.privacyandterms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duoswipe.R
import com.example.duoswipe.ui.component.HeadingText

@Composable
fun TermsAndConditionsScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        HeadingText(value = stringResource(id = R.string.terms_and_conditions_heading))
    }
}

@Preview
@Composable
fun PreTermsAndConditions() {
    TermsAndConditionsScreen()
}