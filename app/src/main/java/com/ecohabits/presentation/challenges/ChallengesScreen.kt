package com.ecohabits.presentation.challenges

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.domain.model.Challenge
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.components.EcoListItem
import com.ecohabits.ui.theme.BackgroundBar
import com.ecohabits.ui.theme.EcoHabitsTheme
import com.ecohabits.ui.theme.EcoHabitsTypography
import com.ecohabits.ui.theme.ProgressBar

// ==========================================
// MAIN SCREEN
// ==========================================

@Composable
fun ChallengesScreen() {
    val challenges = remember {
        mutableStateListOf(
            Challenge(1, "Reduce el uso del agua en la ducha", "Ducha de 5 minutos o menos", false, 10),
            Challenge(2, "Apaga las luces", "Al salir de una habitación", false, 10),
            Challenge(3, "Usa bolsas reutilizables", "Evita las bolsas de plástico", false, 10),
            Challenge(4, "Desconecta aparatos", "Si no los estás usando", false, 10),
            Challenge(5, "Recicla", "Separa papel, plástico y vidrio", false, 10)
        )
    }

    val completedCount = challenges.count { it.challengeStatus }
    val currentProgress = if (challenges.isNotEmpty()) {
        completedCount.toFloat() / challenges.size
    } else 0f

    AtomWallpaper(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            // Esto asegura que todos los EcoCards tengan una separación uniforme
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

            items(challenges) { challenge ->
                AtomCheckBox(
                    title = challenge.titleChallenge,
                    subtitle = challenge.descriptionChallenge,
                    isChecked = challenge.challengeStatus,
                    onCheckedChange = { isChecked ->
                        val index = challenges.indexOf(challenge)
                        if (index != -1) {
                            challenges[index] = challenge.copy(challengeStatus = isChecked)
                        }
                    }
                )
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
    // Título ahora envuelto en un EcoCard con su respectivo Icono y Tipografía
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
    // Barra de progreso ahora dentro de su propia tarjeta
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
                progress = animatedProgress,
                color = ProgressBar,
                trackColor = BackgroundBar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp) // Un poquito más gruesa para resaltar
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
    // Cada checkbox ahora es una tarjeta clickeable separada
    EcoCard(
        modifier = modifier.fillMaxWidth(),
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
    EcoHabitsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ChallengesScreen()
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewChallengesDarkMode() {
    EcoHabitsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ChallengesScreen()
        }
    }
}