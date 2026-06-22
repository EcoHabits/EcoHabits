package com.ecohabits.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.theme.EcoHabitsTheme
import com.ecohabits.ui.theme.EcoHabitsTypography
import com.ecohabits.ui.theme.GreenSuccess

@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            HomeTopBar()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item { Spacer(modifier = Modifier.height(40.dp)) }
                item { GreetingSection(uiState.userName, uiState.streakDays) }
                item { WeatherCard(uiState.weather, uiState.cityName) }
                item { SummaryStatsSection(uiState.totalPoints, uiState.streakDays) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GreenSuccess)
            }
        }
    }
}

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Eco,
            contentDescription = null,
            tint = GreenSuccess,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "EcoHabits",
            style = EcoHabitsTypography().titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun GreetingSection(userName: String, streakDays: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Hola! $userName 👋",
            style = EcoHabitsTypography().headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Llevas $streakDays días consecutivos",
            style = EcoHabitsTypography().bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WeatherCard(weather: WeatherUiState, cityName: String) {
    EcoCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFF64B5F6) // Color azul para el clima
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cityName,
                style = EcoHabitsTypography().titleMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Icon(
                imageVector = getWeatherIcon(weather.condition),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = weather.condition,
                style = EcoHabitsTypography().headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = weather.advice,
                style = EcoHabitsTypography().bodyMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun getWeatherIcon(condition: String): ImageVector {
    return when (condition.uppercase()) {
        "SUNNY", "HOT" -> Icons.Default.WbSunny
        "RAINY", "CLOUDY" -> Icons.Default.Cloud
        "COLD" -> Icons.Default.AcUnit
        else -> Icons.Default.Cloud
    }
}

@Composable
fun SummaryStatsSection(points: Int, streak: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SummaryStatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Star,
            label = "Hoy",
            value = points.toString(),
            unit = "puntos ganados",
            iconTint = Color(0xFFFFB74D)
        )
        SummaryStatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.LocalFireDepartment,
            label = "Racha",
            value = streak.toString(),
            unit = "días seguidos",
            iconTint = Color(0xFFFF7043)
        )
    }
}

@Composable
fun SummaryStatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String,
    unit: String,
    iconTint: Color
) {
    EcoCard(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = iconTint
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    style = EcoHabitsTypography().labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = EcoHabitsTypography().headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = unit,
                style = EcoHabitsTypography().labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    EcoHabitsTheme {
        HomeScreen(
            uiState = HomeUiState(
                userName = "Gab",
                cityName = "Monterrey",
                streakDays = 5,
                weather = WeatherUiState(
                    condition = "Sunny",
                    temperature = "28°C",
                    advice = "Aprovecha el clima de hoy para acciones sostenibles"
                ),
                totalPoints = 250
            )
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDarkHomeScreen() {
    EcoHabitsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen()
        }
    }
}
