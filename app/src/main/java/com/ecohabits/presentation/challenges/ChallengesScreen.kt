package com.ecohabits.presentation.challenges

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.HabitCategory
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.components.EcoListItem
import com.ecohabits.ui.theme.BackgroundBar
import com.ecohabits.ui.theme.EcoHabitsTheme
import com.ecohabits.ui.theme.EcoHabitsTypography
import com.ecohabits.ui.theme.ProgressBar

@Composable
fun ChallengesScreen(
    uiState: ChallengesUiState,
    onChallengeCheckedChange: (Challenge, Boolean) -> Unit,
    onRetry: () -> Unit
) {
    val completedCount = uiState.challenges.count { it.isCompleted }
    val currentProgress = if (uiState.challenges.isNotEmpty()) {
        completedCount.toFloat() / uiState.challenges.size
    } else 0f

    AtomWallpaper(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (uiState.error != null) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "¡Ups! Algo salió mal",
                        style = EcoHabitsTypography().headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = uiState.error,
                        style = EcoHabitsTypography().bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Reintentar")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        AtomChallengueUppBar()
                    }

                    item {
                        AtomProgressBar(
                            progress = currentProgress,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    items(uiState.challenges) { challenge ->
                        AtomCheckBox(
                            title = challenge.title,
                            subtitle = challenge.description,
                            isChecked = challenge.isCompleted,
                            onCheckedChange = { isChecked ->
                                onChallengeCheckedChange(challenge, isChecked)
                            }
                        )
                    }
                    
                    if (uiState.challenges.isEmpty()) {
                        item {
                            Text(
                                text = "No hay desafíos disponibles para tu ubicación actual.",
                                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                                textAlign = TextAlign.Center,
                                style = EcoHabitsTypography().bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// COMPONENTS (ATOMS)
// ==========================================

@Composable
fun AtomWallpaper(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    EcoHabitsTheme(darkTheme = darkTheme) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Composable
fun AtomChallengueUppBar(modifier: Modifier = Modifier) {
    EcoCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Assignment,
                contentDescription = "Challenge icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(40.dp)
            )
            Text(
                text = "Desafíos del Día",
                style = EcoHabitsTypography().headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun AtomProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    EcoCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Progreso Diario",
                style = EcoHabitsTypography().titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            val animatedProgress by animateFloatAsState(
                targetValue = progress,
                animationSpec = tween(durationMillis = 500),
                label = "progressAnimation"
            )

            LinearProgressIndicator(
                progress = { animatedProgress },
                color = ProgressBar,
                trackColor = BackgroundBar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
fun AtomCheckBox(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    EcoCard(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isChecked) 0.6f else 1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.padding(start = 8.dp)
            )

            EcoListItem(
                icon = null,
                title = title,
                subtitle = subtitle
            )
        }
    }
}

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewScreen() {
    val mockState = ChallengesUiState(
        challenges = listOf(
            Challenge("1", "Reto 1", "Desc 1", HabitCategory.AGUA, 10, false),
            Challenge("2", "Reto 2", "Desc 2", HabitCategory.ENERGIA, 20, true)
        )
    )
    EcoHabitsTheme {
        ChallengesScreen(mockState, { _, _ -> }, {})
    }
}
