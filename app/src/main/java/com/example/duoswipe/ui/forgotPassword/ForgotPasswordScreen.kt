package com.example.duoswipe.ui.forgotPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.R
import com.example.duoswipe.core.Utils.Companion.showMessage
import com.example.duoswipe.ui.component.ButtonComponent
import com.example.duoswipe.ui.component.TextField
import com.example.duoswipe.ui.forgotPassword.component.ForgotPassword

@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel= hiltViewModel()) {

    val context = LocalContext.current
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
            TextField(
                textValue = email,
                onValueChange = { newValue ->
                    email = newValue
                },
                labelValue = stringResource(id = R.string.e_mail),
                painterResource(id = R.drawable.baseline_alternate_email_24)
            )
            ButtonComponent(
                onClick = { viewModel.sendPasswordEmail(email.text) },
                value = stringResource(id = R.string.reset)
            )
        }
    }
    ForgotPassword(
        showResetPasswordMessage = {
            showMessage(context, "We've sent you an email with a link to reset the password.")
        },
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )
}
