package com.ecohabits.presentation.auth.signin

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ecohabits.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@Composable
fun SignInRoute(
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateToHome()
        }
    }

    SignInScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onAgeChange = viewModel::onAgeChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = {
            // No implementado aún
        },
        onGoogleSignInClick = {
            Log.d("SignInRoute", "Botón Google clickeado")
            scope.launch {
                try {
                    val activity = context.findActivity()
                    if (activity == null) {
                        Log.e("SignInRoute", "No se encontró la actividad")
                        return@launch
                    }
                    Log.d("SignInRoute", "Actividad encontrada: ${activity.localClassName}")
                    
                    val credentialManager = CredentialManager.create(context)
                    Log.d("SignInRoute", "CredentialManager creado")

                    // 1. Generar Nonce
                    val rawNonce = UUID.randomUUID().toString()
                    val bytes = rawNonce.toByteArray()
                    val md = MessageDigest.getInstance("SHA-256")
                    val digest = md.digest(bytes)
                    val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
                    Log.d("SignInRoute", "Nonce generado")

                    // 2. Configurar petición de Google (Súper permisiva)
                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                        .setAutoSelectEnabled(false)
                        .setNonce(hashedNonce)
                        .build()
                    Log.d("SignInRoute", "Petición configurada con Client ID: ${BuildConfig.GOOGLE_WEB_CLIENT_ID}")

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .setPreferImmediatelyAvailableCredentials(false)
                        .build()

                    // 3. Lanzar selector
                    Log.d("SignInRoute", "Lanzando selector de credenciales...")
                    val result = credentialManager.getCredential(
                        request = request,
                        context = activity,
                    )
                    Log.d("SignInRoute", "Resultado obtenido de CredentialManager")

                    // 4. Extraer Token
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    Log.d("SignInRoute", "Token extraído: ${googleIdToken.take(10)}...")

                    // 5. Enviar al ViewModel
                    viewModel.onGoogleSignIn(googleIdToken, rawNonce)

                } catch (e: Exception) {
                    Log.e("SignInRoute", "Error en Google Sign In: ${e.message}", e)
                }
            }
        },
        onBackClick = onNavigateBack
    )
}

// Helper para encontrar la actividad desde el contexto de Compose
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
