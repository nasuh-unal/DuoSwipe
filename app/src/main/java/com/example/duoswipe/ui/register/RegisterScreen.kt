package com.example.duoswipe.ui.register

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duoswipe.ui.CommonText
import com.example.duoswipe.ui.component.GoogleSignIn
import com.example.duoswipe.ui.component.OneTapSignIn
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException

@Composable
fun RegisterScreen(
    //navController: NavController,
    authViewModel: AuthViewModel,
    loginState: MutableState<Boolean>? = null
) {
    //val registerUiState by registerViewModel.registerUiState.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credentials =
                        authViewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    authViewModel.signInWithGoogle(credentials)
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

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(
                    text = "Create Account,",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ) {}
                Spacer(modifier = Modifier.height(5.dp))
                CommonText(
                    text = "Sign up to get started!",
                    fontSize = 28.sp,
                    color = Color.Black
                ) {}
            }
            Spacer(modifier = Modifier.height(60.dp))
//            CommonTextField(
//                text = registerUiState.fullName,
//                placeholder = "Full Name",
//                isPasswordTextField = false,
//                onValueChange = { registerUiState.fullName = it }
//            )
            Spacer(modifier = Modifier.height(16.dp))
//            CommonTextField(
//                text = registerUiState.eMail,
//                placeholder = "Email",
//                onValueChange = { registerUiState.eMail = it },
//                isPasswordTextField = false
//            )
            Spacer(modifier = Modifier.height(16.dp))
//            CommonTextField(
//                text = registerUiState.password,
//                placeholder = "Password",
//                onValueChange = { registerUiState.password = it },
//                isPasswordTextField = true
//            )
            Spacer(modifier = Modifier.weight(0.2f))
//            CommonLoginButton(text = "Register", modifier = Modifier.fillMaxWidth()) {
//                if (registerUiState.eMail.isNotBlank() && registerUiState.password.isNotBlank() && registerUiState.fullName.isNotBlank()) {
//                    println("Kayit Basarili")
//                    navController.navigate("login_screen")
//                } else {
//                    println("Kayit Basarisiz")
//                }
//            }
            Spacer(modifier = Modifier.height(24.dp))
//            CommonGoogleButton(text = "Connect with Google",onClick=onSignInClick)
            Button(onClick = { authViewModel.oneTapSignIn() }) {

            }
            Spacer(modifier = Modifier.weight(0.3f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(text = "I'm a new user,", fontSize = 18.sp) {}
                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = "Sign In",
//                    Modifier.clickable { navController.navigate("login_screen") }
//                    )
            }
        }
    }
    OneTapSignIn(launch = { launch(it) })

    GoogleSignIn {
        loginState?.let { it.value = false }
    }
}

//@Preview
//@Composable
//fun previewRegister() {
//    val a = rememberNavController()
//    RegisterScreen(a)
//}