package com.ecohabits.presentation.progress

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.theme.EcoHabitsTheme
import com.ecohabits.ui.theme.EcoHabitsTypography
import com.ecohabits.ui.theme.GreenSuccess
import java.util.Locale


@Composable
fun ProgressScreen(
    uiState: ProgressUiState
) {

    when {

        uiState.isLoading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()

            }
        }


        uiState.error != null -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = uiState.error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )

            }
        }


        else -> {

            ProgressContent(
                uiState = uiState
            )

        }
    }
}


@Composable
private fun ProgressContent(
    uiState: ProgressUiState
) {

    val unlockedBadges = uiState.badges.count {
        it.unlocked
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        item {

            ProgressTitle()

        }


        item {

            StatsCards(
                streak = uiState.streak.toString(),
                points = uiState.points.toString(),
                badges = unlockedBadges.toString()
            )

        }


        item {

            ImpactBox(
                savedWater = formatMetric(
                    uiState.metrics.waterSavedLiters
                ),
                co2Reduction = formatMetric(
                    uiState.metrics.co2ReducedKg
                ),
                avoidedWaste = formatMetric(
                    uiState.metrics.wasteReducedKg
                )
            )

        }


        item {

            BadgesSection(
                badges = uiState.badges
            )

        }

    }
}


private fun formatMetric(
    value: Double
): String {

    return if (value % 1.0 == 0.0) {

        value.toInt().toString()

    } else {

        String.format(
            Locale.US,
            "%.1f",
            value
        )

    }
}


@Composable
fun ProgressTitle() {

    EcoCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = "Resumen del Progreso",
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
                tint = MaterialTheme.colorScheme.primary
            )

        }

    }
}


@Composable
fun StatsCards(
    streak: String,
    points: String,
    badges: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {


        StreakCard(
            streak = streak,
            modifier = Modifier.weight(1f)
        )


        PointsCard(
            points = points,
            modifier = Modifier.weight(1f)
        )


        BadgesCard(
            badges = badges,
            modifier = Modifier.weight(1f)
        )

    }
}


@Composable
fun StreakCard(
    streak: String,
    modifier: Modifier = Modifier
) {

    EcoCard(
        modifier = modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Streak Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xE2F4511E)
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = streak,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = "Racha días",
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )

        }

    }
}


@Composable
fun PointsCard(
    points: String,
    modifier: Modifier = Modifier
) {

    EcoCard(
        modifier = modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Icon(
                imageVector = Icons.Default.AutoGraph,
                contentDescription = "Points Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF1E88E5)
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = points,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = "Puntos",
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )

        }

    }
}


@Composable
fun BadgesCard(
    badges: String,
    modifier: Modifier = Modifier
) {

    EcoCard(
        modifier = modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = "Badges Icon",
                modifier = Modifier.size(30.dp),
                tint =
                    if (isSystemInDarkTheme()) {

                        Color(0xFF8A68E5)

                    } else {

                        Color(0xFF5D34BF)

                    }
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = badges,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = "Insignias",
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.padding(10.dp)
            )

        }

    }
}


@Composable
fun ImpactBox(
    savedWater: String,
    co2Reduction: String,
    avoidedWaste: String
) {

    EcoCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {


        Text(
            text = "Impacto Ambiental",
            textAlign = TextAlign.Center,
            style = EcoHabitsTypography().titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 15.dp,
                    bottom = 10.dp
                )
        )


        WaterImpact(
            savedWater = savedWater
        )


        Co2Impact(
            co2Reduction = co2Reduction
        )


        WasteImpact(
            avoidedWaste = avoidedWaste
        )

    }
}


@Composable
fun WaterImpact(
    savedWater: String
) {

    EcoCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 5.dp
            ),
        containerColor = Color(0x2036BDAD)
    ) {


        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                imageVector = Icons.Default.Water,
                tint = Color(0xFF10BBAD),
                contentDescription = "Water Icon",
                modifier = Modifier.size(40.dp)
            )


            Spacer(
                modifier = Modifier.padding(
                    horizontal = 8.dp
                )
            )


            Column {


                Text(
                    text = "Agua ahorrada",
                    style = EcoHabitsTypography().titleMedium
                )


                Row {


                    Text(
                        text = savedWater,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF109BBB)
                    )


                    Spacer(
                        modifier = Modifier.padding(
                            horizontal = 3.dp
                        )
                    )


                    Text(
                        text = "Litros"
                    )

                }

            }

        }

    }
}


@Composable
fun Co2Impact(
    co2Reduction: String
) {

    EcoCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 5.dp
            ),
        containerColor = Color(0x3C7CB342)
    ) {


        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                imageVector = Icons.Default.Eco,
                tint = Color(0xE83EA743),
                contentDescription = "CO2 Icon",
                modifier = Modifier.size(40.dp)
            )


            Spacer(
                modifier = Modifier.padding(
                    horizontal = 8.dp
                )
            )


            Column {


                Text(
                    text = "CO2 reducido",
                    style = EcoHabitsTypography().titleMedium
                )


                Row {


                    Text(
                        text = co2Reduction,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xE83EA743)
                    )


                    Spacer(
                        modifier = Modifier.padding(
                            horizontal = 3.dp
                        )
                    )


                    Text(
                        text = "kg"
                    )

                }

            }

        }

    }
}


@Composable
fun WasteImpact(
    avoidedWaste: String
) {

    EcoCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 5.dp
            )
            .padding(
                bottom = 10.dp
            ),
        containerColor = Color(0x43886B60)
    ) {


        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                imageVector = Icons.Default.AutoAwesome,
                tint = Color.White,
                contentDescription = "Trash Icon",
                modifier = Modifier.size(40.dp)
            )


            Spacer(
                modifier = Modifier.padding(
                    horizontal = 8.dp
                )
            )


            Column {


                Text(
                    text = "Residuos evitados",
                    style = EcoHabitsTypography().titleMedium
                )


                Row {


                    Text(
                        text = avoidedWaste,
                        fontWeight = FontWeight.Bold,
                        color =
                            if (isSystemInDarkTheme()) {

                                Color(0xFFCDA08F)

                            } else {

                                Color(0xFF725043)

                            }
                    )


                    Spacer(
                        modifier = Modifier.padding(
                            horizontal = 3.dp
                        )
                    )


                    Text(
                        text = "kg"
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


    Text(
        text = "Insignias",
        style = EcoHabitsTypography().titleLarge,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )


    if (badges.isEmpty()) {


        EcoCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {


            Text(
                text = "Aún no hay insignias calculadas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

        }


        return
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        val rows = badges.chunked(2)


        rows.forEach { rowBadges ->


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                rowBadges.forEach { badge ->


                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        BadgeItem(
                            badge = badge
                        )

                    }

                }


                if (rowBadges.size == 1) {

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                }

            }

        }

    }
}


@Composable
fun BadgeItem(
    badge: BadgeUiState,
    onClick: (() -> Unit)? = null
) {


    val cardModifier = Modifier
        .fillMaxWidth()
        .let { modifier ->

            if (onClick != null) {

                modifier.clickable {
                    onClick()
                }

            } else {

                modifier

            }

        }


    EcoCard(
        modifier = cardModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation =
                if (badge.unlocked) {
                    4.dp
                } else {
                    1.dp
                }
        ),
        containerColor =
            if (badge.unlocked) {

                MaterialTheme.colorScheme.surfaceVariant

            } else {

                MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.5f
                )

            }
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color =
                            if (badge.unlocked) {

                                badge.backgroundColor

                            } else {

                                badge.backgroundColor.copy(
                                    alpha = 0.3f
                                )

                            },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {


                Icon(
                    imageVector = badge.icon,
                    contentDescription = badge.title,
                    modifier = Modifier.size(40.dp),
                    tint =
                        if (badge.unlocked) {

                            Color.White

                        } else {

                            Color.White.copy(
                                alpha = 0.5f
                            )

                        }
                )

            }


            Text(
                text = badge.title,
                style = EcoHabitsTypography().titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color =
                    if (badge.unlocked) {

                        MaterialTheme.colorScheme.onSurface

                    } else {

                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.5f
                        )

                    }
            )


            if (!badge.unlocked) {


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {


                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Bloqueado",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.error
                    )


                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )


                    Text(
                        text = "Bloqueada",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )

                }


            } else {


                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Desbloqueada",
                    modifier = Modifier.size(16.dp),
                    tint = GreenSuccess
                )

            }

        }

    }
}


/*
 * Este estado SOLO se utiliza para las previews de Android Studio.
 *
 * No son datos reales del usuario.
 */
private val previewProgressUiState = ProgressUiState(

    points = 50,

    level = 1,

    metrics = ImpactMetricsUiState(
        waterSavedLiters = 0.0,
        co2ReducedKg = 0.0,
        wasteReducedKg = 0.0
    ),

    badges = emptyList(),

    streak = 0,

    isLoading = false,

    error = null
)


@Preview(
    showSystemUi = true
)
@Composable
fun PreviewProgressScreen() {

    EcoHabitsTheme {

        ProgressScreen(
            uiState = previewProgressUiState
        )

    }
}


@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDarkProgressScreen() {

    EcoHabitsTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            ProgressScreen(
                uiState = previewProgressUiState
            )

        }

    }
}