package com.ecohabits.presentation.auth.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _uiState.update { 
            it.copy(
                email = email,
                isEmailValid = isEmailValid || email.isEmpty(),
                isFormValid = isEmailValid && it.password.length >= 6
            ) 
        }
    }

    fun onPasswordChange(password: String) {
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
        _uiState.update { 
            it.copy(
                password = password,
                isFormValid = isEmailValid && password.length >= 6
            ) 
        }
    }

    fun login(onSuccess: () -> Unit) {
        if (!_uiState.value.isFormValid) return
        
        _uiState.update { it.copy(isLoading = true) }
        
        // Simular validación
        onSuccess()
        _uiState.update { it.copy(isLoading = false) }
    }
}
