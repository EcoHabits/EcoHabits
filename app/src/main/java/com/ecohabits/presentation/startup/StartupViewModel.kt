package com.ecohabits.presentation.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.AuthSessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            StartupUiState()
        )

    val uiState: StateFlow<StartupUiState> =
        _uiState.asStateFlow()

    init {
        observeStartup()
    }

    private fun observeStartup() {

        viewModelScope.launch {

            updateProgress(
                progress = 0.10f,
                message = "Iniciando EcoHabits..."
            )

            delay(120)

            updateProgress(
                progress = 0.25f,
                message = "Restaurando tu sesión..."
            )

            authRepository.authState.collectLatest { authState ->

                when (authState) {

                    AuthSessionState.Loading -> {

                        updateProgress(
                            progress = 0.40f,
                            message = "Verificando tu sesión..."
                        )
                    }

                    AuthSessionState.Authenticated -> {

                        updateProgress(
                            progress = 0.60f,
                            message = "Cargando tu perfil..."
                        )

                        delay(120)

                        updateProgress(
                            progress = 0.78f,
                            message = "Sincronizando tu progreso..."
                        )

                        delay(120)

                        updateProgress(
                            progress = 0.92f,
                            message = "Preparando tus hábitos..."
                        )

                        delay(100)

                        updateProgress(
                            progress = 1f,
                            message = "¡Todo listo!"
                        )

                        delay(180)

                        _uiState.update {
                            it.copy(
                                destination =
                                    StartupDestination.Authenticated
                            )
                        }
                    }

                    AuthSessionState.Unauthenticated -> {

                        updateProgress(
                            progress = 0.75f,
                            message = "Preparando inicio de sesión..."
                        )

                        delay(120)

                        updateProgress(
                            progress = 1f,
                            message = "¡Todo listo!"
                        )

                        delay(160)

                        _uiState.update {
                            it.copy(
                                destination =
                                    StartupDestination.Unauthenticated
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateProgress(
        progress: Float,
        message: String
    ) {

        val safeProgress =
            progress.coerceIn(
                0f,
                1f
            )

        _uiState.update {
            it.copy(
                progress = safeProgress,
                percentage =
                    (safeProgress * 100)
                        .toInt(),
                message = message
            )
        }
    }
}