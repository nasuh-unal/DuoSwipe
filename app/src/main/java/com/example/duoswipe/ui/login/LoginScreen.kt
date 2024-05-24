package com.example.duoswipe.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.duoswipe.ui.CommonGoogleButton
import com.example.duoswipe.ui.CommonLoginButton
import com.example.duoswipe.ui.CommonText
import com.example.duoswipe.ui.CommonTextField

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonText(
                    text = "Sign in to continue!",
                    fontSize = 28.sp,
                    color = Color.Black
                ) {}
            }
            Spacer(modifier = Modifier.height(60.dp))
            CommonTextField(
                text = email,
                placeholder = "Email",
                onValueChange = { email = it },
                isPasswordTextField = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            CommonTextField(
                text = password,
                placeholder = "Password",
                onValueChange = { password = it },
                isPasswordTextField = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Forgot Password?",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(0.1f))
            CommonLoginButton(text = "Login", modifier = Modifier.fillMaxWidth()) {
                if (email == "adem" && password == "12345") {
                    println("Giris basarili.")
                } else {
                    println("Giris basarisiz.")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            CommonGoogleButton(text = "Connect with Google")
            Spacer(modifier = Modifier.weight(0.4f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(text = "I'm a new user,", fontSize = 18.sp) {}
                Spacer(modifier = Modifier.width(4.dp))
                CommonText(
                    text = "Sign Up",
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                ) {
                    navController.navigate("register_screen")
                }
            }
        }
    }
}
@Preview
@Composable
fun previewLoginScreen() {
    val navController = rememberNavController()
    LoginScreen(navController)
}