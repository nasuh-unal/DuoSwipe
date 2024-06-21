package com.example.duoswipe.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.duoswipe.ui.CommonGoogleButton
import com.example.duoswipe.ui.CommonLoginButton
import com.example.duoswipe.ui.CommonText
import com.example.duoswipe.ui.CommonTextField

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val registerUiState by registerViewModel.registerUiState.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
            CommonTextField(
                text = registerUiState.fullName,
                placeholder = "Full Name",
                isPasswordTextField = false,
                onValueChange = { registerUiState.fullName = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CommonTextField(
                text = registerUiState.eMail,
                placeholder = "Email",
                onValueChange = { registerUiState.eMail = it },
                isPasswordTextField = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            CommonTextField(
                text = registerUiState.password,
                placeholder = "Password",
                onValueChange = { registerUiState.password = it },
                isPasswordTextField = true
            )
            Spacer(modifier = Modifier.weight(0.2f))
            CommonLoginButton(text = "Register", modifier = Modifier.fillMaxWidth()) {
                if (registerUiState.eMail.isNotBlank() && registerUiState.password.isNotBlank() && registerUiState.fullName.isNotBlank()) {
                    println("Kayit Basarili")
                    navController.navigate("login_screen")
                } else {
                    println("Kayit Basarisiz")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            CommonGoogleButton(text = "Connect with Google")
            Spacer(modifier = Modifier.weight(0.3f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(text = "I'm a new user,", fontSize = 18.sp) {}
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Sign In",
                    Modifier.clickable { navController.navigate("login_screen") }
                    )
            }
        }
    }
}

@Preview
@Composable
fun previewRegister() {
    val a = rememberNavController()
    RegisterScreen(a)
}