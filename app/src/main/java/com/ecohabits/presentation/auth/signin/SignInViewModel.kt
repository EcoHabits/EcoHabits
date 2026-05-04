package com.ecohabits.presentation.auth.signin

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { 
            it.copy(
                name = name,
                isFormValid = validateForm(name, it.age, it.email, it.password)
            ) 
        }
    }

    fun onAgeChange(age: String) {
        // Solo permitir números
        if (age.isNotEmpty() && !age.all { it.isDigit() }) return
        
        _uiState.update { 
            it.copy(
                age = age,
                isFormValid = validateForm(it.name, age, it.email, it.password)
            ) 
        }
    }

    fun onEmailChange(email: String) {
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _uiState.update { 
            it.copy(
                email = email,
                isEmailValid = isEmailValid || email.isEmpty(),
                isFormValid = validateForm(it.name, it.age, email, it.password) && isEmailValid
            ) 
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { 
            it.copy(
                password = password,
                isFormValid = validateForm(it.name, it.age, it.email, password)
            ) 
        }
    }

    private fun validateForm(name: String, age: String, email: String, password: String): Boolean {
        return name.isNotBlank() && 
               age.isNotBlank() && 
               android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && 
               password.length >= 6
    }

    fun signIn(onSuccess: () -> Unit) {
        if (!_uiState.value.isFormValid) return
        
        _uiState.update { it.copy(isLoading = true) }
        
        // Aquí iría la lógica con el repositorio (Supabase, etc.)
        // Por ahora simulamos éxito inmediato
        onSuccess()
        _uiState.update { it.copy(isLoading = false) }
    }
}
