package com.ecohabits.presentation.progress

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter


data class ProgressUiState(
    val points: Int,
    val level: Int,
    val metrics: ImpactMetricsUiState,
    val badges: List<BadgeUiState>,
    val streak: Int,
    val isLoading: Boolean = false
)

data class ImpactMetricsUiState(
    val waterSavedLiters: Double,
    val co2ReducedKg: Double,
    val wasteReducedKg: Double
)

data class BadgeUiState(
    val id: String,
    val title: String,
    val icon: Painter,
    val backgroundColor: Color,
    val unlocked: Boolean
)
