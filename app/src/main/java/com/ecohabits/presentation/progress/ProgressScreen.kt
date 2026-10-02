package com.ecohabits.presentation.progress

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.theme.EcoHabitsTheme
import java.util.Locale
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.border
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush


private const val POINTS_PER_LEVEL = 500


@Composable
fun ProgressScreen(
    uiState: ProgressUiState,
    onDismissBadgeUnlock: () -> Unit = {}
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when {

            uiState.isLoading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(0xFFF5F7F5)
                        ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = Color(
                            0xFF43A047
                        )
                    )
                }
            }


            uiState.error != null -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text =
                            uiState.error,
                        style =
                            MaterialTheme
                                .typography
                                .bodyLarge,
                        color =
                            MaterialTheme
                                .colorScheme
                                .error,
                        textAlign =
                            TextAlign.Center
                    )
                }
            }


            else -> {

                ProgressContent(
                    uiState =
                        uiState
                )
            }
        }


        uiState
            .newlyUnlockedBadge
            ?.let { badge ->

                BadgeUnlockOverlay(
                    badge = badge,
                    onDismiss =
                        onDismissBadgeUnlock
                )
            }
    }
}


@Composable
private fun ProgressContent(
    uiState: ProgressUiState
) {

    val unlockedBadges =
        uiState.badges.count {
            it.unlocked
        }

    val background =
        if (
            androidx.compose.foundation
                .isSystemInDarkTheme()
        ) {
            MaterialTheme
                .colorScheme
                .background
        } else {
            Color(
                0xFFF5F7F5
            )
        }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                background
            )
    ) {

        LiquidProgressBackground()


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(
                    16.dp
                )
        ) {

            item {

                Spacer(
                    modifier =
                        Modifier.height(
                            8.dp
                        )
                )
            }


            item {

                ProgressHeader(
                    points =
                        uiState.points
                )
            }


            item {

                StatsCards(
                    streak =
                        uiState.streak,
                    points =
                        uiState.points,
                    badges =
                        unlockedBadges
                )
            }


            item {

                LevelProgressCard(
                    level =
                        uiState.level,
                    points =
                        uiState.points
                )
            }


            item {

                ImpactSection(
                    savedWater =
                        uiState.metrics
                            .waterSavedLiters,
                    co2Reduction =
                        uiState.metrics
                            .co2ReducedKg,
                    avoidedWaste =
                        uiState.metrics
                            .wasteReducedKg
                )
            }


            item {

                BadgesSection(
                    badges =
                        uiState.badges
                )
            }


            item {

                Spacer(
                    modifier =
                        Modifier.height(
                            24.dp
                        )
                )
            }
        }
    }
}


@Composable
private fun ProgressHeader(
    points: Int
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                28.dp
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color(
                        0xFFE5F2E6
                    )
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    0.dp
            )
    ) {

        Box(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Column(
                modifier =
                    Modifier.padding(
                        20.dp
                    )
            ) {

                Text(
                    text =
                        "Resumen del progreso",
                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(
                            0xFF267D43
                        )
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            5.dp
                        )
                )


                Text(
                    text =
                        progressMessage(
                            points
                        ),
                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,
                    color =
                        Color(
                            0xFF5E7564
                        )
                )
            }


            Box(
                modifier =
                    Modifier
                        .align(
                            Alignment.CenterEnd
                        )
                        .padding(
                            end = 20.dp
                        )
                        .size(
                            52.dp
                        )
                        .clip(
                            CircleShape
                        )
                        .background(
                            Color.White.copy(
                                alpha = 0.72f
                            )
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Eco,
                    contentDescription =
                        null,
                    tint =
                        Color(
                            0xFF43A047
                        ),
                    modifier =
                        Modifier.size(
                            29.dp
                        )
                )
            }
        }
    }
}


@Composable
private fun StatsCards(
    streak: Int,
    points: Int,
    badges: Int
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(
                10.dp
            )
    ) {

        StatCard(
            modifier =
                Modifier.weight(
                    1f
                ),
            value =
                streak.toString(),
            label =
                "Racha",
            secondary =
                if (streak == 1) {
                    "día"
                } else {
                    "días"
                },
            icon =
                Icons.Default
                    .LocalFireDepartment,
            iconColor =
                Color(
                    0xFFF4511E
                ),
            background =
                Color(
                    0xFFFFF2EC
                )
        )


        StatCard(
            modifier =
                Modifier.weight(
                    1f
                ),
            value =
                points.toString(),
            label =
                "Puntos",
            secondary =
                "totales",
            icon =
                Icons.Default
                    .AutoGraph,
            iconColor =
                Color(
                    0xFF147BC3
                ),
            background =
                Color(
                    0xFFEAF5FC
                )
        )


        StatCard(
            modifier =
                Modifier.weight(
                    1f
                ),
            value =
                badges.toString(),
            label =
                "Insignias",
            secondary =
                "logradas",
            icon =
                Icons.Default
                    .AutoAwesome,
            iconColor =
                Color(
                    0xFF6D46C7
                ),
            background =
                Color(
                    0xFFF2EDFC
                )
        )
    }
}


@Composable
private fun StatCard(
    modifier: Modifier,
    value: String,
    label: String,
    secondary: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    background: Color
) {

    Card(
        modifier =
            modifier,
        shape =
            RoundedCornerShape(
                24.dp
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    background
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    1.dp
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
                    ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier =
                    Modifier
                        .size(
                            38.dp
                        )
                        .clip(
                            CircleShape
                        )
                        .background(
                            Color.White.copy(
                                alpha = 0.80f
                            )
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        icon,
                    contentDescription =
                        null,
                    tint =
                        iconColor,
                    modifier =
                        Modifier.size(
                            21.dp
                        )
                )
            }


            Spacer(
                modifier =
                    Modifier.height(
                        9.dp
                    )
            )


            Text(
                text =
                    value,
                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Color(
                        0xFF1E2B21
                    )
            )


            Text(
                text =
                    label,
                style =
                    MaterialTheme
                        .typography
                        .labelLarge,
                fontWeight =
                    FontWeight.SemiBold,
                color =
                    Color(
                        0xFF344D3B
                    )
            )


            Text(
                text =
                    secondary,
                style =
                    MaterialTheme
                        .typography
                        .labelSmall,
                color =
                    Color(
                        0xFF79877D
                    )
            )
        }
    }
}


@Composable
private fun LevelProgressCard(
    level: Int,
    points: Int
) {

    val currentLevelPoints =
        points % POINTS_PER_LEVEL

    val progress =
        currentLevelPoints.toFloat() /
                POINTS_PER_LEVEL.toFloat()

    val remaining =
        if (
            currentLevelPoints == 0 &&
            points > 0
        ) {
            POINTS_PER_LEVEL
        } else {
            POINTS_PER_LEVEL -
                    currentLevelPoints
        }


    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                26.dp
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    2.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(
                    18.dp
                )
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    Text(
                        text =
                            "Nivel $level",
                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,
                        fontWeight =
                            FontWeight.Bold
                    )


                    Text(
                        text =
                            "$currentLevelPoints / $POINTS_PER_LEVEL pts",
                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,
                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }


                Surface(
                    shape =
                        RoundedCornerShape(
                            50
                        ),
                    color =
                        Color(
                            0xFF147BC3
                        ).copy(
                            alpha = 0.10f
                        )
                ) {

                    Text(
                        text =
                            "Nivel ${level + 1}",
                        modifier =
                            Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            ),
                        style =
                            MaterialTheme
                                .typography
                                .labelMedium,
                        color =
                            Color(
                                0xFF147BC3
                            ),
                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )


            LinearProgressIndicator(
                progress = {
                    progress.coerceIn(
                        0f,
                        1f
                    )
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            10.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                50
                            )
                        ),
                color =
                    Color(
                        0xFF43A047
                    ),
                trackColor =
                    Color(
                        0xFFE3EBE4
                    )
            )


            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )


            Text(
                text =
                    "Te faltan $remaining pts para alcanzar el nivel ${level + 1}.",
                style =
                    MaterialTheme
                        .typography
                        .bodySmall,
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}


@Composable
private fun ImpactSection(
    savedWater: Double,
    co2Reduction: Double,
    avoidedWaste: Double
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                28.dp
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    2.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(
                    16.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(
                    10.dp
                )
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text =
                        "Impacto ambiental",
                    modifier =
                        Modifier.weight(
                            1f
                        ),
                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,
                    fontWeight =
                        FontWeight.Bold
                )


                Icon(
                    imageVector =
                        Icons.Default.Eco,
                    contentDescription =
                        null,
                    tint =
                        Color(
                            0xFF43A047
                        )
                )
            }


            ImpactCard(
                title =
                    "Agua ahorrada",
                value =
                    formatMetric(
                        savedWater
                    ),
                unit =
                    "Litros",
                icon =
                    Icons.Default.Water,
                iconColor =
                    Color(
                        0xFF11AFC0
                    ),
                background =
                    Color(
                        0xFFE2F7F5
                    )
            )


            ImpactCard(
                title =
                    "CO₂ reducido",
                value =
                    formatMetric(
                        co2Reduction
                    ),
                unit =
                    "kg",
                icon =
                    Icons.Default.Eco,
                iconColor =
                    Color(
                        0xFF43A047
                    ),
                background =
                    Color(
                        0xFFEAF5E2
                    )
            )


            ImpactCard(
                title =
                    "Residuos evitados",
                value =
                    formatMetric(
                        avoidedWaste
                    ),
                unit =
                    "kg",
                icon =
                    Icons.Default.AutoAwesome,
                iconColor =
                    Color(
                        0xFF80665A
                    ),
                background =
                    Color(
                        0xFFF0ECE9
                    )
            )
        }
    }
}


@Composable
private fun ImpactCard(
    title: String,
    value: String,
    unit: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    background: Color
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                20.dp
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    background
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    0.dp
            )
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 14.dp,
                    vertical = 13.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(
                            42.dp
                        )
                        .clip(
                            CircleShape
                        )
                        .background(
                            Color.White.copy(
                                alpha = 0.78f
                            )
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        icon,
                    contentDescription =
                        null,
                    tint =
                        iconColor,
                    modifier =
                        Modifier.size(
                            23.dp
                        )
                )
            }


            Spacer(
                modifier =
                    Modifier.width(
                        13.dp
                    )
            )


            Column(
                modifier =
                    Modifier.weight(
                        1f
                    )
            ) {

                Text(
                    text =
                        title,
                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,
                    fontWeight =
                        FontWeight.SemiBold,
                    color =
                        Color(
                            0xFF34483A
                        )
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            2.dp
                        )
                )


                Row(
                    verticalAlignment =
                        Alignment.Bottom
                ) {

                    Text(
                        text =
                            value,
                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            iconColor
                    )


                    Spacer(
                        modifier =
                            Modifier.width(
                                5.dp
                            )
                    )


                    Text(
                        text =
                            unit,
                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,
                        color =
                            Color(
                                0xFF68776D
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun BadgesSection(
    badges: List<BadgeUiState>
) {

    val unlockedCount =
        badges.count {
            it.unlocked
        }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Insignias",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "Tus logros EcoHabits",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xFF43A047).copy(
                    alpha = 0.11f
                )
            ) {

                Text(
                    text = "$unlockedCount / ${badges.size}",
                    modifier = Modifier.padding(
                        horizontal = 13.dp,
                        vertical = 6.dp
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        if (badges.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {

                Text(
                    text = "Completa desafíos para comenzar a desbloquear insignias.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            return
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(
                12.dp
            )
        ) {

            badges
                .chunked(2)
                .forEach { rowBadges ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.spacedBy(
                                12.dp
                            )
                    ) {

                        rowBadges.forEach { badge ->

                            BadgeItem(
                                badge = badge,
                                modifier =
                                    Modifier.weight(
                                        1f
                                    )
                            )
                        }

                        if (rowBadges.size == 1) {

                            Spacer(
                                modifier =
                                    Modifier.weight(
                                        1f
                                    )
                            )
                        }
                    }
                }
        }
    }
}


@Composable
private fun BadgeItem(
    badge: BadgeUiState,
    modifier: Modifier = Modifier
) {

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "badgeLiquid_${badge.id}"
        )

    val movement by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 5000
                    ),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label = "badgeLiquidMovement"
    )

    val iconScale by
    androidx.compose.animation.core.animateFloatAsState(
        targetValue =
            if (badge.unlocked) {
                1f
            } else {
                0.94f
            },
        animationSpec =
            tween(
                durationMillis = 300
            ),
        label = "badgeIconScale"
    )

    val cardBackground =
        if (badge.unlocked) {

            Brush.linearGradient(
                colors =
                    listOf(
                        badge.backgroundColor.copy(
                            alpha = 0.13f
                        ),
                        Color.White.copy(
                            alpha = 0.96f
                        ),
                        badge.backgroundColor.copy(
                            alpha = 0.07f
                        )
                    )
            )

        } else {

            Brush.linearGradient(
                colors =
                    listOf(
                        Color(0xFFF5F6F5),
                        Color(0xFFEEF0EE)
                    )
            )
        }

    Box(
        modifier =
            modifier
                .height(
                    154.dp
                )
                .clip(
                    RoundedCornerShape(
                        26.dp
                    )
                )
                .background(
                    brush =
                        cardBackground
                )
                .border(
                    width =
                        if (badge.unlocked) {
                            1.dp
                        } else {
                            0.5.dp
                        },
                    color =
                        if (badge.unlocked) {
                            badge.backgroundColor.copy(
                                alpha = 0.18f
                            )
                        } else {
                            Color(0xFFE2E5E2)
                        },
                    shape =
                        RoundedCornerShape(
                            26.dp
                        )
                )
    ) {

        LiquidBadgeBackground(
            color =
                badge.backgroundColor,
            movement =
                movement,
            unlocked =
                badge.unlocked
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 13.dp
                    ),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {

            Box(
                modifier =
                    Modifier.size(
                        60.dp
                    ),
                contentAlignment =
                    Alignment.Center
            ) {

                if (badge.unlocked) {

                    Box(
                        modifier =
                            Modifier
                                .size(
                                    58.dp
                                )
                                .background(
                                    badge.backgroundColor.copy(
                                        alpha = 0.12f
                                    ),
                                    CircleShape
                                )
                    )
                }

                Box(
                    modifier =
                        Modifier
                            .size(
                                47.dp
                            )
                            .scale(
                                iconScale
                            )
                            .clip(
                                CircleShape
                            )
                            .background(
                                if (badge.unlocked) {
                                    badge.backgroundColor
                                } else {
                                    Color(0xFFD1D6D2)
                                }
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            if (badge.unlocked) {
                                badge.icon
                            } else {
                                Icons.Default.Lock
                            },
                        contentDescription =
                            badge.title,
                        tint =
                            Color.White,
                        modifier =
                            Modifier.size(
                                24.dp
                            )
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )

            Text(
                text =
                    badge.title,
                style =
                    MaterialTheme
                        .typography
                        .titleSmall,
                fontWeight =
                    FontWeight.SemiBold,
                textAlign =
                    TextAlign.Center,
                maxLines = 1,
                overflow =
                    TextOverflow.Ellipsis,
                color =
                    if (badge.unlocked) {
                        Color(0xFF26372B)
                    } else {
                        Color(0xFF8A918B)
                    }
            )

            Spacer(
                modifier =
                    Modifier.height(
                        5.dp
                    )
            )

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        if (badge.unlocked) {
                            Icons.Default.AutoAwesome
                        } else {
                            Icons.Default.Lock
                        },
                    contentDescription =
                        null,
                    modifier =
                        Modifier.size(
                            12.dp
                        ),
                    tint =
                        if (badge.unlocked) {
                            badge.backgroundColor
                        } else {
                            Color(0xFFA1A7A2)
                        }
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            4.dp
                        )
                )

                Text(
                    text =
                        if (badge.unlocked) {
                            "Desbloqueada"
                        } else {
                            "Bloqueada"
                        },
                    style =
                        MaterialTheme
                            .typography
                            .labelSmall,
                    fontWeight =
                        FontWeight.Medium,
                    color =
                        if (badge.unlocked) {
                            badge.backgroundColor
                        } else {
                            Color(0xFF929892)
                        }
                )
            }
        }
    }
}


@Composable
private fun LiquidBadgeBackground(
    color: Color,
    movement: Float,
    unlocked: Boolean
) {

    Canvas(
        modifier =
            Modifier.fillMaxSize()
    ) {

        val alphaMultiplier =
            if (unlocked) {
                1f
            } else {
                0.25f
            }

        drawCircle(
            color =
                color.copy(
                    alpha =
                        0.08f *
                                alphaMultiplier
                ),
            radius =
                size.width *
                        0.54f,
            center =
                Offset(
                    x =
                        size.width *
                                (
                                        -0.08f +
                                                movement *
                                                0.17f
                                        ),
                    y =
                        size.height *
                                0.13f
                )
        )

        drawCircle(
            color =
                color.copy(
                    alpha =
                        0.065f *
                                alphaMultiplier
                ),
            radius =
                size.width *
                        0.42f,
            center =
                Offset(
                    x =
                        size.width *
                                (
                                        1.05f -
                                                movement *
                                                0.17f
                                        ),
                    y =
                        size.height *
                                0.88f
                )
        )

        if (unlocked) {

            drawCircle(
                color =
                    Color.White.copy(
                        alpha = 0.28f
                    ),
                radius =
                    size.width *
                            0.15f,
                center =
                    Offset(
                        x =
                            size.width *
                                    (
                                            0.73f -
                                                    movement *
                                                    0.07f
                                            ),
                        y =
                            size.height *
                                    0.18f
                    )
            )
        }
    }
}


@Composable
private fun LiquidProgressBackground() {

    val transition =
        rememberInfiniteTransition(
            label = "progressLiquid"
        )


    val movement by
    transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis =
                            7000
                    ),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label =
            "progressLiquidMovement"
    )


    Canvas(
        modifier =
            Modifier.fillMaxSize()
    ) {

        drawCircle(
            color =
                Color(
                    0xFF43A047
                ).copy(
                    alpha = 0.035f
                ),
            radius =
                size.width *
                        0.58f,
            center =
                Offset(
                    x =
                        size.width *
                                (
                                        -0.08f +
                                                movement *
                                                0.10f
                                        ),
                    y =
                        size.height *
                                0.17f
                )
        )


        drawCircle(
            color =
                Color(
                    0xFF147BC3
                ).copy(
                    alpha = 0.03f
                ),
            radius =
                size.width *
                        0.52f,
            center =
                Offset(
                    x =
                        size.width *
                                (
                                        1.04f -
                                                movement *
                                                0.10f
                                        ),
                    y =
                        size.height *
                                0.72f
                )
        )
    }
}


private fun progressMessage(
    points: Int
): String {

    return when {

        points == 0 ->
            "Tu progreso comienza con un pequeño hábito."

        points < 250 ->
            "Cada hábito suma. Sigue avanzando 🌱"

        points < 500 ->
            "Estás construyendo una gran racha verde."

        points < 1000 ->
            "Tu impacto ya está creciendo bastante 🌿"

        else ->
            "Tus hábitos están generando un gran impacto."
    }
}


private fun formatMetric(
    value: Double
): String {

    return if (
        value % 1.0 == 0.0
    ) {

        value
            .toInt()
            .toString()

    } else {

        String.format(
            Locale.US,
            "%.1f",
            value
        )
    }
}


/*
 * Solo para previews.
 */
private val previewProgressUiState =
    ProgressUiState(
        points = 410,
        level = 1,
        metrics =
            ImpactMetricsUiState(
                waterSavedLiters =
                    20.0,
                co2ReducedKg =
                    2.2,
                wasteReducedKg =
                    0.3
            ),
        badges =
            emptyList(),
        newlyUnlockedBadge =
            null,
        streak = 1,
        isLoading =
            false,
        error =
            null
    )


@Preview(
    showSystemUi = true
)
@Composable
fun PreviewProgressScreen() {

    EcoHabitsTheme {

        ProgressScreen(
            uiState =
                previewProgressUiState
        )
    }
}


@Preview(
    showSystemUi = true,
    uiMode =
        Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDarkProgressScreen() {

    EcoHabitsTheme {

        Surface(
            modifier =
                Modifier.fillMaxSize()
        ) {

            ProgressScreen(
                uiState =
                    previewProgressUiState
            )
        }
    }
}