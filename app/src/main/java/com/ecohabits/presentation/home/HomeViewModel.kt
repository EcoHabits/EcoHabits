package com.ecohabits.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.usecase.GetCurrentContextUseCase
import com.ecohabits.domain.usecase.GetDailyChallengesUseCase
import com.ecohabits.domain.usecase.GetUserProgressUseCase
import com.ecohabits.domain.usecase.GetUserStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyChallengesUseCase: GetDailyChallengesUseCase,
    private val getCurrentContextUseCase: GetCurrentContextUseCase,
    private val getUserProgressUseCase: GetUserProgressUseCase,
    private val getUserStreakUseCase: GetUserStreakUseCase,
    private val authRepository: AuthRepository,
) : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            HomeUiState()
        )


    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()


    init {

        loadHomeData()
    }


    fun loadHomeData() {

        if (_uiState.value.isLoading) {
            return
        }


        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true
                )
            }


            Log.d(
                "HomeViewModel",
                "Iniciando carga de datos..."
            )


            val context =
                getCurrentContextUseCase()


            /*
             * Primero aseguramos que los retos
             * estén generados y asignados.
             */
            val challengesResult =
                getDailyChallengesUseCase()


            challengesResult
                .exceptionOrNull()
                ?.let { error ->

                    Log.w(
                        "HomeViewModel",
                        "No se pudieron cargar los retos diarios: ${error.message}"
                    )
                }


            /*
             * Puntos reales.
             */
            val progressResult =
                getUserProgressUseCase()


            val totalPoints =
                progressResult
                    .getOrNull()
                    ?.points
                    ?: 0


            progressResult
                .exceptionOrNull()
                ?.let { error ->

                    Log.w(
                        "HomeViewModel",
                        "No se pudo cargar el progreso: ${error.message}"
                    )
                }


            /*
             * Racha real.
             */
            val streakResult =
                getUserStreakUseCase()


            val streakDays =
                streakResult
                    .getOrElse {
                        0
                    }


            streakResult
                .exceptionOrNull()
                ?.let { error ->

                    Log.w(
                        "HomeViewModel",
                        "No se pudo calcular la racha: ${error.message}"
                    )
                }


            _uiState.update { state ->

                state.copy(

                    isLoading =
                        false,

                    userName =
                        context
                            ?.userName
                            ?: "Usuario",

                    cityName =
                        context
                            ?.cityName
                            ?: "Ubicación desconocida",

                    weather =
                        state.weather.copy(

                            condition =
                                context
                                    ?.weatherCondition
                                    ?.name
                                    ?: "Desconocido",

                            advice =
                                "Aprovecha el clima de hoy para acciones sostenibles"
                        ),

                    totalPoints =
                        totalPoints,

                    streakDays =
                        streakDays
                )
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = authRepository.logout()
            _uiState.update { it.copy(isLoading = false) }
            if (result.isSuccess) {
                onSuccess()
            } else {
                Log.e("HomeViewModel", "Error al cerrar sesión: ${result.exceptionOrNull()?.message}")
            }
        }
    }
}