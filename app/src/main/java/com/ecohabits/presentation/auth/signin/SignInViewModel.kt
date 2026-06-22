package com.ecohabits.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { 
            it.copy(
                name = name,
                isFormValid = validateForm(name, it.age, it.email, it.password),
                error = null
            ) 
        }
    }

    fun onAgeChange(age: String) {
        if (age.isNotEmpty() && !age.all { it.isDigit() }) return
        
        _uiState.update { 
            it.copy(
                age = age,
                isFormValid = validateForm(it.name, age, it.email, it.password),
                error = null
            ) 
        }
    }

    fun onEmailChange(email: String) {
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _uiState.update { 
            it.copy(
                email = email,
                isEmailValid = isEmailValid || email.isEmpty(),
                isFormValid = validateForm(it.name, it.age, email, it.password) && isEmailValid,
                error = null
            ) 
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { 
            it.copy(
                password = password,
                isFormValid = validateForm(it.name, it.age, it.email, password),
                error = null
            ) 
        }
    }

    private fun validateForm(name: String, age: String, email: String, password: String): Boolean {
        return (name.isNotBlank() && 
               age.isNotBlank() && 
               android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && 
               password.length >= 6)
    }

    fun onGoogleSignIn(idToken: String, rawNonce: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = signInWithGoogleUseCase(idToken, rawNonce)
            
            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al iniciar sesión con Google") }
            }
        }
    }

    fun onError(message: String) {
        _uiState.update { it.copy(error = message, isLoading = false) }
    }

    fun signUp(onSuccess: () -> Unit) {
        if (!_uiState.value.isFormValid) return
        
        _uiState.update { it.copy(isLoading = true, error = null) }

        onSuccess()
        _uiState.update { it.copy(isLoading = false) }
    }
}
