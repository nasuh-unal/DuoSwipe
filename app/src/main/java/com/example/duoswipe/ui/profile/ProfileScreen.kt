package com.example.duoswipe.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import android.app.Activity
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duoswipe.R
import com.example.duoswipe.core.Utils
import com.example.duoswipe.data.model.AuthState
import com.example.duoswipe.data.model.DataProvider
import com.example.duoswipe.ui.component.MyBottomBar
import com.example.duoswipe.ui.component.OneTapSignIn
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit,
    navigateToOverviewScreen: () -> Unit
) {
    val context = LocalContext.current
    val openLoginDialog = remember { mutableStateOf(false) }
    val openDeleteAccountAlertDialog = remember { mutableStateOf(false) }
    val authState = DataProvider.authState

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credential =
                        viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    viewModel.deleteAccount(credential.googleIdToken)
                } catch (e: ApiException) {
                    Log.e("HomeScreen:Launcher", "Re-auth error: $e")
                }
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }
    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        bottomBar = {
            MyBottomBar(navigateToOverviewScreen, navigateToProfileScreen)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {viewModel.signOut()},
                modifier = Modifier.offset(y = 60.dp),
                contentColor = Color.White,
                containerColor = Color(android.graphics.Color.parseColor("#fe5b52"))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                    contentDescription = "Exit",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(), // Box ile tüm genişliği kaplıyoruz
                        contentAlignment = Alignment.Center // İçindeki içeriği ortalıyoruz
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    if (authState == AuthState.SignedIn) {
                        Text(
                            DataProvider.user?.displayName ?: "Name Placeholder",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            DataProvider.user?.email ?: "Email Placeholder",
                            color = Color.Black
                            )
                    } else {
                        Text(
                            "Sign-in to view data!"
                        )
                    }
                }
            }

            Row {
                Button(
                    onClick = {
                        if (authState != AuthState.SignedIn)
                            openLoginDialog.value = true
                        else
                            viewModel.signOut()
                    },
                    modifier = Modifier
                        .size(width = 160.dp, height = 50.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = if (authState != AuthState.SignedIn) "Sign-in" else "Sign out",
                        modifier = Modifier.padding(6.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Button(
                    onClick = {
                        openDeleteAccountAlertDialog.value = true
                    },
                    modifier = Modifier
                        .size(width = 200.dp, height = 50.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    )
                ) {
                    Text(
                        text = "Delete Account",
                        modifier = Modifier.padding(6.dp),
                        color = Color.Red
                    )
                }
            }

            AnimatedVisibility(visible = openDeleteAccountAlertDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDeleteAccountAlertDialog.value = false },
                    title = { Text("Delete Account") },
                    text = {
                        Text("Deleting account is permanent. Are you sure you want to delete your account?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.checkNeedsReAuth()
                                openDeleteAccountAlertDialog.value = false
                            }
                        ) {
                            Text("Yes, Delete", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            openDeleteAccountAlertDialog.value = false
                        }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }
        }
    }


    OneTapSignIn(
        launch = {
            launch(it)
        },
        showErrorMessage = { errorMessage ->
            Utils.showMessage(context, errorMessage)
        }
    )
}