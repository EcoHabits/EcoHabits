package com.ecohabits.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import com.ecohabits.ui.components.NavigationItem

object NavItems {
    val bottomNavItems = listOf(
        NavigationItem(
            icon = Icons.Default.Home,
            label = "Inicio",
            route = "home"
        ),
        NavigationItem(
            icon = Icons.Default.EmojiEvents,
            label = "Retos",
            route = "challenges"
        ),
        NavigationItem(
            icon = Icons.Default.BarChart,
            label = "Progreso",
            route = "progress"
        )
    )
}
