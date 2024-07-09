package com.example.duoswipe.ui.signUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duoswipe.R
import com.example.duoswipe.ui.component.HeadingText
import com.example.duoswipe.ui.component.NormalText
import com.example.duoswipe.ui.component.PasswordTextField
import com.example.duoswipe.ui.component.TextField

@Composable
fun SignUpScreen() {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalText(value = stringResource(id = R.string.hi_there))
            HeadingText(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                labelValue = stringResource(id = R.string.first_name),
                painterResource = painterResource(id = R.drawable.baseline_person_24)
            )
            TextField(
                labelValue = stringResource(id = R.string.last_name),
                painterResource(id = R.drawable.baseline_person_24)
            )
            TextField(
                labelValue = stringResource(id = R.string.e_mail),
                painterResource(id = R.drawable.baseline_alternate_email_24)
            )
            PasswordTextField(
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.sharp_add_moderator_24)
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}