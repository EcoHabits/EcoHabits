package com.ecohabits.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Colores extra
val GreenSuccess = Color(0xFF4CAF50)
val YellowWarning = Color(0xFFFFC107)
val EcoCardLightBackground = Color(0xFFE8F5E8)
val EcoCardDarkBackground = Color(0xE93C4B3C)

val ProgressBar = Color(0xFF2E7D32)

val BackgroundBar = Color(0xFFC836C9)


// Light theme
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFA5D6A7),
    onPrimaryContainer = Color(0xFF1B5E20),
    secondary = Color(0xFF8D6E63),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFF4DED5),
    tertiary = Color(0xFF795548),
    background = Color(0xFFFEFBFF),
    onBackground = Color(0xFF1B1C18),
    surface = Color(0xFFFEFBFF),
    onSurface = Color(0xFF1B1C18),
    surfaceVariant = Color(0xFFE7E0EC),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    outline = Color(0xFF79747E),
)

// Dark theme
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF003300),
    primaryContainer = Color(0xFF005300),
    onPrimaryContainer = Color(0xFFA5D6A7),
    secondary = Color(0xFFB69F8C),
    onSecondary = Color(0xFF2A1810),
    secondaryContainer = Color(0xFF40362B),
    tertiary = Color(0xFF8D6E63),
    background = Color(0xFF13181D),
    onBackground = Color(0xFFE2E3DC),
    surface = Color(0xFF0F1419),
    onSurface = Color(0xFFE2E3DC),
    surfaceVariant = Color(0xFF43483F),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    outline = Color(0xFF8E9286)
)
