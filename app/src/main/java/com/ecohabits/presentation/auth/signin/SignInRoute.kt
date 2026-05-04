package com.ecohabits.presentation.auth.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SignInRoute(
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SignInScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onAgeChange = viewModel::onAgeChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = {
            viewModel.signIn(onSuccess = onNavigateToLogin)
        },
        onBackClick = onNavigateBack
    )
}
