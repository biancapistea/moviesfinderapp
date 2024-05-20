package com.example.moviesfinderapp.ui.biometric

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BiometricAuthManager(private val activity: AppCompatActivity) {
    private val channel = Channel<BiometricResults>()
    val promptResults = channel.receiveAsFlow()
    fun showBiometricPrompt(title: String, description: String) {
        val manager = BiometricManager.from(activity)
        val authenticators = if (Build.VERSION.SDK_INT >= 30) {
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        } else {
            BIOMETRIC_STRONG
        }

        val promptInfo = PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators).setConfirmationRequired(false)
        if (Build.VERSION.SDK_INT < 30) {
            promptInfo.setNegativeButtonText("Cancel")
        }

        when (manager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                channel.trySend(BiometricResults.HardwareUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                channel.trySend(BiometricResults.FeatureUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                channel.trySend(BiometricResults.AuthenticationNotSet)
                return
            }

            else -> Unit
        }

        val prompt = BiometricPrompt(
            activity, object: BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    channel.trySend(BiometricResults.AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    channel.trySend(BiometricResults.AuthenticationFailed)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    channel.trySend(BiometricResults.AuthenticationError(errString.toString()))
                }

            }
        )
        prompt.authenticate(promptInfo.build())

    }

    sealed interface BiometricResults {
        object HardwareUnavailable : BiometricResults
        object FeatureUnavailable : BiometricResults
        data class AuthenticationError(val error: String) : BiometricResults
        object AuthenticationFailed : BiometricResults
        object AuthenticationSuccess : BiometricResults
        object AuthenticationNotSet : BiometricResults

    }
}