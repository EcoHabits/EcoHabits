package com.ecohabits.presentation.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.model.HabitCategory
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
    private val getCurrentContextUseCase: GetCurrentContextUseCase
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
            _uiState.update { state ->
                state.copy(
                    weather = state.weather.copy(
                        condition = context?.weatherCondition?.name ?: "Desconocido",
                        advice = if (context != null) "Cargando retos para ${context.cityName}..." else "Cargando retos..."
                    )
                )
            }

            // 2. Obtener retos del orquestador
            getDailyChallengesUseCase()
                .onSuccess { challenges ->
                    Log.d("HomeViewModel", "Retos obtenidos: ${challenges.size}")
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            recommendations = challenges.take(3).map { challenge ->
                                RecommendationUiState(
                                    icon = mapCategoryToIcon(challenge.category),
                                    text = challenge.title,
                                    points = challenge.points
                                )
                            },
                            specialChallenge = challenges.lastOrNull()?.let { challenge ->
                                SpecialChallengeUiState(
                                    title = challenge.title,
                                    description = challenge.description,
                                    points = challenge.points,
                                    co2Reduction = "${(challenge.points * 0.05).toInt()}kg", // Cálculo ficticio
                                    icon = mapCategoryToIcon(challenge.category)
                                )
                            } ?: state.specialChallenge
                        )
                    }
                }
                .onFailure { error ->
                    Log.e("HomeViewModel", "Error al cargar retos: ${error.message}")
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun mapCategoryToIcon(category: HabitCategory): ImageVector {
        return when (category) {
            HabitCategory.AGUA -> Icons.Default.Opacity
            HabitCategory.ENERGIA -> Icons.Default.ElectricBolt
            HabitCategory.RESIDUOS -> Icons.Default.Recycling
            HabitCategory.MOVILIDAD -> Icons.AutoMirrored.Filled.DirectionsBike
            HabitCategory.GENERAL -> Icons.Default.AutoAwesome
        }
    }
}
