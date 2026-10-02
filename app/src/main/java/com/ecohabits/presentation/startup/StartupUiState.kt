package com.ecohabits.presentation.startup

sealed interface StartupDestination {

    data object Loading : StartupDestination

    data object Authenticated : StartupDestination

    data object Unauthenticated : StartupDestination
}


data class StartupUiState(

    val progress: Float = 0f,

    val percentage: Int = 0,

    val message: String = "Preparando EcoHabits...",

    val destination: StartupDestination =
        StartupDestination.Loading
)