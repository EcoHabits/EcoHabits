package com.ecohabits.presentation.progress

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ProgressUiState(
    val points: Int = 0,
    val level: Int = 1,
    val metrics: ImpactMetricsUiState = ImpactMetricsUiState(),
    val badges: List<BadgeUiState> = emptyList(),
    val streak: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ImpactMetricsUiState(
    val waterSavedLiters: Double = 0.0,
    val co2ReducedKg: Double = 0.0,
    val wasteReducedKg: Double = 0.0
)

data class BadgeUiState(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val unlocked: Boolean
)