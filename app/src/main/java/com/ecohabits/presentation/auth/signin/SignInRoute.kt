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
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
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

                    val googleApiAvailability = GoogleApiAvailability.getInstance()
                    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
                    if (resultCode != ConnectionResult.SUCCESS) {
                        Log.e("SignInRoute", "Google Play Services no disponible: $resultCode")
                        if (googleApiAvailability.isUserResolvableError(resultCode)) {
                            googleApiAvailability.getErrorDialog(activity, resultCode, 9000)?.show()
                        } else {
                            viewModel.onError("Este dispositivo no es compatible con Google Sign In")
                        }
                        return@launch
                    }

                    if (BuildConfig.GOOGLE_WEB_CLIENT_ID.isBlank()) {
                        Log.e("SignInRoute", "GOOGLE_WEB_CLIENT_ID no está configurado")
                        return@launch
                    }

                    val rawNonce = UUID.randomUUID().toString()
                    val bytes = rawNonce.toByteArray()
                    val md = MessageDigest.getInstance("SHA-256")
                    val digest = md.digest(bytes)
                    val hashedNonce = android.util.Base64.encodeToString(digest, android.util.Base64.NO_WRAP or android.util.Base64.NO_PADDING or android.util.Base64.URL_SAFE)
                    Log.d("SignInRoute", "Nonce generado (Base64)")

                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                        .setAutoSelectEnabled(false)
                        .setNonce(hashedNonce)
                        .build()
                    Log.d("SignInRoute", "Petición configurada con Client ID: ${BuildConfig.GOOGLE_WEB_CLIENT_ID}")
                    Log.d("SignInRoute", "GoogleIdOption - filterByAuthorizedAccounts: false")
                    Log.d("SignInRoute", "GoogleIdOption - autoSelectEnabled: false")
                    Log.d("SignInRoute", "GoogleIdOption - nonce set: ${hashedNonce != null}")

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .setPreferImmediatelyAvailableCredentials(false)
                        .build()

                    Log.d("SignInRoute", "Lanzando selector de credenciales...")
                    val result = credentialManager.getCredential(
                        request = request,
                        context = activity,
                    )
                    Log.d("SignInRoute", "Resultado obtenido de CredentialManager")

                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    Log.d("SignInRoute", "Token extraído: ${googleIdToken.take(10)}...")

                    viewModel.onGoogleSignIn(googleIdToken, rawNonce)

                } catch (e: NoCredentialException) {
                    Log.e("SignInRoute", "No se encontraron credenciales: ${e.message}")
                    viewModel.onError("No se encontraron cuentas de Google. Asegúrate de tener una cuenta activa en el dispositivo.")
                } catch (e: GetCredentialException) {
                    Log.e("SignInRoute", "Error de Credential Manager: ${e.message}")
                    viewModel.onError("Error al obtener credenciales: ${e.message}")
                } catch (e: Exception) {
                    Log.e("SignInRoute", "Error inesperado en Google Sign In: ${e.message}", e)
                    viewModel.onError("Error inesperado: ${e.message}")
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
