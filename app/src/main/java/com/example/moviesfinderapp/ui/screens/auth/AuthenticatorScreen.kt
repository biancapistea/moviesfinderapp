package com.example.moviesfinderapp.ui.screens.auth

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.moviesfinderapp.ui.biometric.BiometricAuthManager
import com.example.moviesfinderapp.ui.components.buttons.ButtonComponent

@Composable
fun AuthenticatorScreen(authManager: BiometricAuthManager, onNavigateToDashboard: () -> Unit) {
    val biometricResult by authManager.promptResults.collectAsState(
        initial = null
    )
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            Log.d("HELLO", "Activity result is $it")
        })

    LaunchedEffect(biometricResult) {
        if (biometricResult == BiometricAuthManager.BiometricResults.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You need to authenticate in order to use this app.", color = Color.White)
        ButtonComponent(onClick = {
            authManager.showBiometricPrompt(
                title = "Touch ID Movies App",
                description = "Use Touch ID for authenticate in app."
            )
        }, text = "Authenticate")
        biometricResult?.let { result ->
            Text(
                text = when (result) {
                    is BiometricAuthManager.BiometricResults.AuthenticationError -> {
                        result.error
                    }

                    BiometricAuthManager.BiometricResults.AuthenticationFailed -> {
                        "Authentication failed."
                    }

                    BiometricAuthManager.BiometricResults.AuthenticationNotSet -> {
                        "Authentication is not set."
                    }

                    BiometricAuthManager.BiometricResults.FeatureUnavailable -> {
                        "The Touch ID is not supported."
                    }

                    else -> ""
                }
            )

        }

        biometricResult?.let { result ->
            when (result) {
                is BiometricAuthManager.BiometricResults.AuthenticationError -> {
                    result.error
                }

                BiometricAuthManager.BiometricResults.AuthenticationFailed -> {
                    "Authentication failed."
                }

                BiometricAuthManager.BiometricResults.AuthenticationNotSet -> {
                    "Authentication is not set."
                }

                BiometricAuthManager.BiometricResults.FeatureUnavailable -> {
                    "The Touch ID is not supported."
                }

                BiometricAuthManager.BiometricResults.AuthenticationSuccess -> {
                    onNavigateToDashboard()
                }

                else -> null

            }
        }
    }
}