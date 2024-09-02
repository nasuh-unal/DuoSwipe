package com.example.duoswipe.ui.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duoswipe.R
import com.example.duoswipe.core.Utils.Companion.showMessage
import com.example.duoswipe.ui.component.ButtonComponent
import com.example.duoswipe.ui.component.ClickableLoginOrSignUpTextComponent
import com.example.duoswipe.ui.component.DividerTextComponent
import com.example.duoswipe.ui.component.HeadingText
import com.example.duoswipe.ui.component.NormalText
import com.example.duoswipe.ui.component.PasswordTextField
import com.example.duoswipe.ui.component.TextField
import com.example.duoswipe.ui.component.UnderLineText
import com.example.duoswipe.ui.signIn.component.SignIn

@Composable
fun SignInScreen(
    viewModel:SignInViewModel
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            NormalText(value = stringResource(id = R.string.hi_there))
            HeadingText(value = stringResource(id = R.string.welcome_back))
            Spacer(modifier = Modifier.height(25.dp))
            TextField(
                textValue = email,
                onValueChange = { newValue ->
                    email = newValue
                },
                labelValue = stringResource(id = R.string.e_mail),
                painterResource(id = R.drawable.baseline_alternate_email_24)
            )
            PasswordTextField(
                textValue = password,
                onValueChange = { newValue ->
                    password = newValue
                },
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.sharp_add_moderator_24)
            )
            Spacer(modifier = Modifier.height(15.dp))
            UnderLineText(value = stringResource(id = R.string.forgot_password))
            Spacer(modifier = Modifier.height(150.dp))
            ButtonComponent(
                onClick = { viewModel.signInWithEmailAndPassword(email.text,password.text) },
                value = stringResource(id = R.string.login)
            )
            Spacer(modifier = Modifier.height(25.dp))
            DividerTextComponent()
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // GoogleButtonComponent()
            }
            Spacer(modifier = Modifier.height(15.dp))
            ClickableLoginOrSignUpTextComponent(tryingToLogin = false, onTextSelected = {})
        }
    }
    SignIn(
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )
}

@Preview
@Composable
fun PreLoginScreen() {
    //SignInScreen()
}