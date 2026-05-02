package com.ecohabits.presentation.progress

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Water
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


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
    val icon: ImageVector,
    val backgroundColor: Color,
    val unlocked: Boolean
)

public val mock = listOf(
    BadgeUiState(
        id = "1",
        title = "Guardián del Agua",
        icon = Icons.Default.Water,
        backgroundColor = Color(0xFF4FC3F7),
        unlocked = true
    ),
    BadgeUiState(
        id = "2",
        title = "Estrella Verde",
        icon = Icons.Default.Eco,
        backgroundColor = Color(0xFF81C784),
        unlocked = true
    ),
    BadgeUiState(
        id = "3",
        title = "Eco Guerrero",
        icon = Icons.Default.AutoAwesome,
        backgroundColor = Color(0xFFFFB74D),
        unlocked = true
    ),
    BadgeUiState(
        id = "4",
        title = "Maestro Reciclador",
        icon = Icons.Default.Autorenew,
        backgroundColor = Color(0xFF64B5F6),
        unlocked = true
    ),
    BadgeUiState(
        id = "5",
        title = "Defensor Energético",
        icon = Icons.Default.FlashOn,
        backgroundColor = Color(0xFFFF8A65),
        unlocked = false  // Bloqueada
    ),
    BadgeUiState(
        id = "6",
        title = "Campeón Ambiental",
        icon = Icons.Default.Star,
        backgroundColor = Color(0xFFBA68C8),
        unlocked = false  // Bloqueada
    )
)