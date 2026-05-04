package com.ecohabits.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BrightnessLow
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeUiState(
    val userName: String = "Usuario",
    val streakDays: Int = 12,
    val weather: WeatherUiState = mockWeather,
    val recommendations: List<RecommendationUiState> = mockRecommendations,
    val specialChallenge: SpecialChallengeUiState = mockSpecialChallenge,
    val totalPoints: Int = 150,
    val isLoading: Boolean = false
)

data class WeatherUiState(
    val condition: String,
    val temperature: String,
    val advice: String
)

data class RecommendationUiState(
    val icon: ImageVector,
    val text: String,
    val points: Int
)

data class SpecialChallengeUiState(
    val title: String,
    val description: String,
    val points: Int,
    val co2Reduction: String,
    val icon: ImageVector
)

// Mocks
val mockWeather = WeatherUiState(
    condition = "Soleado",
    temperature = "22°C",
    advice = "Aprovecha el clima de hoy para acciones sostenibles"
)

val mockRecommendations = listOf(
    RecommendationUiState(Icons.AutoMirrored.Filled.DirectionsBike, "Usa bicicleta o camina, el clima es perfecto", 40),
    RecommendationUiState(Icons.Default.BrightnessLow, "Seca ropa al aire libre en vez de secadora", 30),
    RecommendationUiState(Icons.Default.Opacity, "Riega plantas temprano para evitar evaporación", 25)
)

val mockSpecialChallenge = SpecialChallengeUiState(
    title = "Desafío Especial del Día",
    description = "Usa transporte sostenible todo el día",
    points = 100,
    co2Reduction = "5kg",
    icon = Icons.Default.AutoAwesome
)
