package com.ecohabits.presentation.auth.signin

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ecohabits.BuildConfig
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
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
        if (uiState.isSuccess) onNavigateToHome()
    }

    SignInScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onAgeChange = viewModel::onAgeChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { /* Registro tradicional no implementado */ },
        onGoogleSignInClick = {
            scope.launch {
                try {
                    val activity = context.findActivity() ?: return@launch
                    val credentialManager = CredentialManager.create(context)

                    // Verificación de disponibilidad de Google Play Services
                    val googleApiAvailability = GoogleApiAvailability.getInstance()
                    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
                    if (resultCode != ConnectionResult.SUCCESS) {
                        if (googleApiAvailability.isUserResolvableError(resultCode)) {
                            googleApiAvailability.getErrorDialog(activity, resultCode, 9000)?.show()
                        } else {
                            viewModel.onError("Dispositivo no compatible con Google Sign In")
                        }
                        return@launch
                    }

                    if (BuildConfig.GOOGLE_WEB_CLIENT_ID.isBlank()) {
                        viewModel.onError("Error de configuración: Client ID no encontrado")
                        return@launch
                    }

                    val rawNonce = UUID.randomUUID().toString()
                    
                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                        .setAutoSelectEnabled(false)
                        .setNonce(rawNonce)
                        .build()

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .setPreferImmediatelyAvailableCredentials(false)
                        .build()

                    val result = credentialManager.getCredential(request = request, context = activity)
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                    
                    viewModel.onGoogleSignIn(googleIdTokenCredential.idToken, rawNonce)

                } catch (e: NoCredentialException) {
                    viewModel.onError("No se encontró ninguna cuenta de Google activa en el dispositivo")
                } catch (e: GetCredentialException) {
                    viewModel.onError("Error de conexión: ${e.message}")
                } catch (e: Exception) {
                    viewModel.onError("Ocurrió un error inesperado al intentar iniciar sesión")
                }
            }
        },
        onBackClick = onNavigateBack
    )
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
