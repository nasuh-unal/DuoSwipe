package com.example.duoswipe.ui.signUp

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.example.duoswipe.ui.component.CheckBoxComponent
import com.example.duoswipe.ui.component.ClickableLoginOrSignUpTextComponent
import com.example.duoswipe.ui.component.DividerTextComponent
import com.example.duoswipe.ui.component.GoogleButtonComponent
import com.example.duoswipe.ui.component.HeadingText
import com.example.duoswipe.ui.component.NormalText
import com.example.duoswipe.ui.component.OneTapSignIn
import com.example.duoswipe.ui.component.PasswordTextField
import com.example.duoswipe.ui.component.TextField
import com.example.duoswipe.ui.signUp.components.GoogleSignIn
import com.example.duoswipe.ui.signUp.components.SendEmailVerification
import com.example.duoswipe.ui.signUp.components.SignUp
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException

@Composable
@ExperimentalComposeUiApi
fun SignUpScreen(
    viewModel: SignUpViewModel ,
    loginState: MutableState<Boolean>? = null
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    viewModel.signInWithGoogle(credentials)
                } catch (e: ApiException) {
                    Log.e("LoginScreen:Launcher", "Login One-tap $e")
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.e("LoginScreen:Launcher", "OneTapClient Canceled")
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

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
            HeadingText(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(50.dp))
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
            CheckBoxComponent(stringResource(id = R.string.terms_and_conditions), {})
            Spacer(modifier = Modifier.height(60.dp))
            ButtonComponent(
                onClick = { viewModel.signUpWithEmailAndPassword(email.text, password.text) },
                value = stringResource(id = R.string.register)
            )
            Spacer(modifier = Modifier.height(35.dp))
            DividerTextComponent()
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                GoogleButtonComponent(onClick = { viewModel.oneTapSignIn() })
            }
            Spacer(modifier = Modifier.height(15.dp))
            ClickableLoginOrSignUpTextComponent(true, {})
        }
    }

    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification()
        },
        showVerifyEmailMessage = {
            showMessage(context, "We've sent you an email with a link to verify the email.")
        },
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )
    SendEmailVerification()



    OneTapSignIn(
        launch = {
            launch(it)
        },
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )

    GoogleSignIn {
        // Dismiss LoginScreen
        loginState?.let {
            it.value = false
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    //SignUpScreen()
}