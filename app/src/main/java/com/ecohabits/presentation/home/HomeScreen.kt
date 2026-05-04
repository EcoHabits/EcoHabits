package com.ecohabits.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.presentation.progress.BadgeItem
import com.ecohabits.presentation.progress.BadgeUiState
import com.ecohabits.ui.components.BottomNavigationBar
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.components.NavigationItem
import com.ecohabits.ui.theme.EcoHabitsTheme
import com.ecohabits.ui.theme.EcoHabitsTypography

val navMock = listOf(
    NavigationItem(Icons.Default.Home, "Inicio", "home"),
    NavigationItem(Icons.AutoMirrored.Filled.List, "Retos", "challenges"),
    NavigationItem(Icons.Default.AutoGraph, "Progreso", "progress")
)

@Composable
fun HomeScreen(
    points: String,
    level: Int,
    days: Int,
    badges: List<BadgeUiState>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 0,
                onItemSelected = {},
                items = navMock
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HomeTitle() }
            item { StatsCards(streak = "No Usado", level = level, days = days, points = points) }
            item { SeeChallenges() }
            item { BadgesSection(badges = badges) }
        }
    }
}

@Composable
fun HomeTitle(modifier: Modifier = Modifier) {
    EcoCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "EcoHabits",
                style = EcoHabitsTypography().headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = "Eco icon",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(50.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun StatsCards(streak: String, level: Int, days: Int, points: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        PointsCard(points)
        LevelCard(level.toString())
        DaysCard(days.toString())
    }
}

@Composable
fun PointsCard(points: String) {
    EcoCard(
        modifier = Modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                imageVector = Icons.Default.StarBorder,
                contentDescription = "Points Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xE2F4511E)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = points,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Puntos",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun LevelCard(level: String) {
    EcoCard(
        modifier = Modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                imageVector = Icons.Default.AutoGraph,
                contentDescription = "Level Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF1E88E5)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = level,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Nivel",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun DaysCard(days: String) {
    EcoCard(
        modifier = Modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Days Streak Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF1E88E5)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = days,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Dias",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun SeeChallenges(modifier: Modifier = Modifier) {
    EcoCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ver Desafios del Dia",
                style = EcoHabitsTypography().headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = "Eco icon",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(50.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun BadgesSection(badges: List<BadgeUiState>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Insignias Desbloqueadas",
            style = EcoHabitsTypography().titleLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val rows = badges.chunked(2)
            rows.forEach { rowBadges ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowBadges.forEach { badge ->
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            BadgeItem(badge = badge)
                        }
                    }

                    if (rowBadges.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    EcoHabitsTheme {
        HomeScreen(
            points = "150",
            level = 5,
            days = 12,
            badges = emptyList()
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDarkHomeScreen() {
    EcoHabitsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                points = "150",
                level = 5,
                days = 12,
                badges = emptyList()
            )
        }
    }
}