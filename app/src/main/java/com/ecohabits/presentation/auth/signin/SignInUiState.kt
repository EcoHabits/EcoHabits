package com.ecohabits.presentation.auth.signin

data class SignInUiState(
    val name: String = "",
    val age: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isEmailValid: Boolean = true,
    val isFormValid: Boolean = false
)
