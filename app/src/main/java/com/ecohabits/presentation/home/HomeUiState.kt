package com.ecohabits.presentation.home

data class HomeUiState(
    val userName: String = "Usuario",
    val cityName: String = "",
    val streakDays: Int = 0,
    val weather: WeatherUiState = WeatherUiState(
        condition = "Cargando...",
        temperature = "--°C",
        advice = "Aprovecha el clima de hoy para acciones sostenibles",
    ),
    val totalPoints: Int = 0,
    val isLoading: Boolean = false
)

data class WeatherUiState(
    val condition: String,
    val temperature: String,
    val advice: String
)
