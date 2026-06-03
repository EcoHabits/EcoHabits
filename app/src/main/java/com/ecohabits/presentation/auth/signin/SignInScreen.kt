package com.ecohabits.presentation.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecohabits.R
import com.ecohabits.ui.components.EcoButton
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.components.EcoTextField
import com.ecohabits.ui.theme.EcoHabitsTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var showPassword by remember { mutableStateOf(value = false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Crear Cuenta",
                        style = EcoHabitsTypography().titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_background),
                contentDescription = "Fondo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.uam_verde_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                EcoCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Registro",
                            style = EcoHabitsTypography().titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        EcoTextField(
                            value = uiState.name,
                            onValueChange = onNameChange,
                            label = "Nombre completo",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        EcoTextField(
                            value = uiState.age,
                            onValueChange = onAgeChange,
                            label = "Edad",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        EcoTextField(
                            value = uiState.email,
                            onValueChange = onEmailChange,
                            label = "Correo electrónico",
                            modifier = Modifier.fillMaxWidth(),
                            isError = !uiState.isEmailValid
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = onPasswordChange,
                            label = { Text("Contraseña") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = (uiState.password.isNotEmpty() && uiState.password.length < 6),
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showPassword = !showPassword }) {
                                    Icon(
                                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (showPassword) "Ocultar" else "Mostrar",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                                unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        EcoButton(
                            text = if (uiState.isLoading) "Cargando..." else "Registrarse",
                            onClick = onSignInClick,
                            enabled = false, // Deshabilitado por ahora como pidió el usuario
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                        
                        Text(
                            text = "Registro tradicional no disponible aún",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f))
                            Text(
                                text = " O ",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            HorizontalDivider(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        OutlinedButton(
                            onClick = onGoogleSignInClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            enabled = !uiState.isLoading,
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = ButtonDefaults.outlinedButtonBorder(enabled = !uiState.isLoading)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Continuar con Google",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                
                uiState.error?.let { error ->
                    LaunchedEffect(error) {
                        snackbarHostState.showSnackbar(error)
                    }
                }
            }
        }
    }
}
