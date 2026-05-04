package com.ecohabits.presentation.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isEmailValid: Boolean = true,
    val isFormValid: Boolean = false
)
