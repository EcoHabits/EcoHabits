package com.ecohabits.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.usecase.GetCurrentContextUseCase
import com.ecohabits.domain.usecase.GetDailyChallengesUseCase
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            Log.d("HomeViewModel", "Iniciando carga de datos...")
            val context = getCurrentContextUseCase()
            
            // Llamamos a GetDailyChallengesUseCase para asegurar que los retos se generen/asignen
            getDailyChallengesUseCase()

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    cityName = context?.cityName ?: "Ubicación desconocida",
                    weather = state.weather.copy(
                        condition = context?.weatherCondition?.name ?: "Desconocido",
                        advice = "Aprovecha el clima de hoy para acciones sostenibles"
                    )
                )
            }
        }
    }
}
