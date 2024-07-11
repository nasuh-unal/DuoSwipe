package com.example.duoswipe.ui.signUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duoswipe.R
import com.example.duoswipe.ui.CommonGoogleButton
import com.example.duoswipe.ui.component.ButtonComponent
import com.example.duoswipe.ui.component.CheckBoxComponent
import com.example.duoswipe.ui.component.DividerTextComponent
import com.example.duoswipe.ui.component.GoogleButtonComponent
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
        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            NormalText(value = stringResource(id = R.string.hi_there))
            HeadingText(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(25.dp))
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
            Spacer(modifier = Modifier.height(10.dp))
            CheckBoxComponent(stringResource(id = R.string.terms_and_conditions))
            Spacer(modifier = Modifier.height(60.dp))
            ButtonComponent(value = stringResource(id = R.string.register))
            Spacer(modifier = Modifier.height(30.dp))
            DividerTextComponent()
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                GoogleButtonComponent()
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}