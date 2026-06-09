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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.components.EcoButton
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.theme.EcoHabitsTheme
import com.ecohabits.ui.theme.EcoHabitsTypography
import com.ecohabits.ui.theme.GreenSuccess

@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState()
) {
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
            item { GreetingSection(uiState.userName, uiState.streakDays) }
            item { WeatherCard(uiState.weather) }
            item { RecommendationsSection(uiState.recommendations) }
            item { SpecialChallengeCard(uiState.specialChallenge) }
            item {
                EcoButton(
                    onClick = { /* TODO */ },
                    text = "Ver Todos los Desafíos",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item { SummaryStatsSection(uiState.totalPoints, uiState.streakDays) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
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
    Column {
        Text(
            text = "¡Hola! $userName 👋",
            style = EcoHabitsTypography().headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Llevas $streakDays días consecutivos",
            style = EcoHabitsTypography().bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun WeatherCard(weather: WeatherUiState) {
    EcoCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFF64B5F6) // Color azul para el clima
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Clima Actual",
                        style = EcoHabitsTypography().labelLarge,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = weather.condition,
                        style = EcoHabitsTypography().headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = weather.temperature,
                        style = EcoHabitsTypography().titleLarge,
                        color = Color.White
                    )
                }
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = weather.advice,
                style = EcoHabitsTypography().bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun RecommendationsSection(recommendations: List<RecommendationUiState>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Recomendaciones para Hoy",
            style = EcoHabitsTypography().titleMedium,
            fontWeight = FontWeight.Bold
        )
        recommendations.forEach { recommendation ->
            RecommendationItem(recommendation)
        }
    }
}

@Composable
fun RecommendationItem(recommendation: RecommendationUiState) {
    EcoCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(GreenSuccess.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = recommendation.icon,
                    contentDescription = null,
                    tint = GreenSuccess,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = recommendation.text,
                style = EcoHabitsTypography().bodyMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            Box(
                modifier = Modifier
                    .background(GreenSuccess, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "+${recommendation.points}",
                    style = EcoHabitsTypography().labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SpecialChallengeCard(challenge: SpecialChallengeUiState) {
    EcoCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = GreenSuccess.copy(alpha = 0.8f)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = challenge.icon,
                    contentDescription = null,
                    tint = GreenSuccess,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "DESAFÍO ESPECIAL",
                    style = EcoHabitsTypography().labelSmall,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = challenge.title,
                    style = EcoHabitsTypography().titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = challenge.description,
                    style = EcoHabitsTypography().bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+${challenge.points} puntos  •  Reduce ${challenge.co2Reduction} de CO2",
                    style = EcoHabitsTypography().labelSmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
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
        HomeScreen()
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
