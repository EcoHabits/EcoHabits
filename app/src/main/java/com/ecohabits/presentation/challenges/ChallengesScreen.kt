package com.ecohabits.presentation.challenges

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.HabitCategory
import kotlinx.coroutines.delay


@Composable
fun ChallengesScreen(
    challenges: List<Challenge>,
    onChallengeClick: (Challenge) -> Unit,
    modifier: Modifier = Modifier
) {

    val completedCount =
        challenges.count {
            it.isCompleted
        }

    val totalCount =
        challenges.size

    val progress =
        if (totalCount > 0) {
            completedCount.toFloat() /
                    totalCount.toFloat()
        } else {
            0f
        }

    val earnedPoints =
        challenges
            .filter {
                it.isCompleted
            }
            .sumOf {
                it.points
            }

    val allCompleted =
        challenges.isNotEmpty() &&
                completedCount == totalCount


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Color(0xFFF5F7F5)
            )
            .windowInsetsPadding(
                WindowInsets.statusBars
            )
            .padding(
                horizontal = 18.dp,
                vertical = 12.dp
            )
    ) {

        ChallengeHeader(
            completedCount = completedCount,
            totalCount = totalCount,
            progress = progress,
            earnedPoints = earnedPoints,
            allCompleted = allCompleted
        )


        Spacer(
            modifier = Modifier.height(
                18.dp
            )
        )


        LazyColumn(
            verticalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )
        ) {

            items(
                items = challenges,
                key = {
                    it.id
                }
            ) { challenge ->

                ModernChallengeCard(
                    challenge = challenge,
                    onToggle = {
                        onChallengeClick(
                            challenge
                        )
                    }
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
private fun ChallengeHeader(
    completedCount: Int,
    totalCount: Int,
    progress: Float,
    earnedPoints: Int,
    allCompleted: Boolean
) {

    val headerColor by animateColorAsState(
        targetValue =
            if (allCompleted) {
                Color(0xFFDAF2DF)
            } else {
                Color(0xFFE7F1E6)
            },
        animationSpec = tween(
            durationMillis = 450
        ),
        label = "headerColor"
    )


    Card(
        shape = RoundedCornerShape(
            26.dp
        ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    headerColor
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Column(
            modifier =
                Modifier.padding(
                    18.dp
                )
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(
                            42.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                13.dp
                            )
                        )
                        .background(
                            Color(
                                0xFF4CAF50
                            ).copy(
                                alpha = 0.14f
                            )
                        ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            if (allCompleted) {
                                Icons.Default.Check
                            } else {
                                Icons.Default.Eco
                            },
                        contentDescription =
                            null,
                        tint =
                            Color(
                                0xFF2E7D32
                            ),
                        modifier =
                            Modifier.size(
                                23.dp
                            )
                    )
                }


                Spacer(
                    modifier =
                        Modifier.width(
                            12.dp
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
                            if (allCompleted) {
                                "¡Día completado! 🌿"
                            } else {
                                "Desafíos del Día"
                            },
                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,
                        fontWeight =
                            FontWeight.SemiBold,
                        color =
                            Color(
                                0xFF23412F
                            )
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                2.dp
                            )
                    )


                    Text(
                        text =
                            "$completedCount de $totalCount completados",
                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,
                        color =
                            Color(
                                0xFF5B7464
                            )
                    )
                }


                Surface(
                    shape =
                        RoundedCornerShape(
                            50
                        ),
                    color =
                        Color.White.copy(
                            alpha = 0.80f
                        )
                ) {

                    Text(
                        text =
                            "$earnedPoints pts",
                        modifier =
                            Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 7.dp
                            ),
                        style =
                            MaterialTheme
                                .typography
                                .labelLarge,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color(
                                0xFF287E49
                            )
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )


            LinearProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        9.dp
                    )
                    .clip(
                        RoundedCornerShape(
                            20.dp
                        )
                    ),
                color =
                    Color(
                        0xFF43A047
                    ),
                trackColor =
                    Color.White.copy(
                        alpha = 0.80f
                    )
            )


            Spacer(
                modifier =
                    Modifier.height(
                        9.dp
                    )
            )


            Text(
                text =
                    when {

                        allCompleted -> {
                            "Excelente trabajo. Completaste todos tus retos de hoy."
                        }

                        totalCount == 0 -> {
                            "Preparando tus desafíos..."
                        }

                        totalCount - completedCount == 1 -> {
                            "Te falta solo 1 reto para completar el día."
                        }

                        else -> {
                            "Te faltan ${totalCount - completedCount} retos para completar el día."
                        }
                    },
                style =
                    MaterialTheme
                        .typography
                        .bodySmall,
                color =
                    Color(
                        0xFF5B7464
                    )
            )
        }
    }
}


@Composable
private fun ModernChallengeCard(
    challenge: Challenge,
    onToggle: () -> Unit
) {

    var showPointsAnimation by
    remember(
        challenge.id
    ) {
        mutableStateOf(
            false
        )
    }


    var previousCompletedState by
    remember(
        challenge.id
    ) {
        mutableStateOf(
            challenge.isCompleted
        )
    }


    LaunchedEffect(
        challenge.isCompleted
    ) {

        if (
            challenge.isCompleted &&
            !previousCompletedState
        ) {

            showPointsAnimation =
                true

            delay(
                950
            )

            showPointsAnimation =
                false
        }

        previousCompletedState =
            challenge.isCompleted
    }


    val backgroundColor by
    animateColorAsState(
        targetValue =
            if (
                challenge.isCompleted
            ) {
                Color(
                    0xFFF0F8F1
                )
            } else {
                Color.White
            },
        animationSpec =
            tween(
                350
            ),
        label =
            "challengeBackground"
    )


    val borderColor by
    animateColorAsState(
        targetValue =
            if (
                challenge.isCompleted
            ) {
                Color(
                    0xFFC0DFC5
                )
            } else {
                Color(
                    0xFFE8ECE8
                )
            },
        animationSpec =
            tween(
                350
            ),
        label =
            "challengeBorder"
    )


    Box(
        modifier =
            Modifier.fillMaxWidth()
    ) {

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
                        backgroundColor
                ),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation =
                        if (
                            challenge.isCompleted
                        ) {
                            0.dp
                        } else {
                            2.dp
                        }
                )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color =
                            borderColor,
                        shape =
                            RoundedCornerShape(
                                26.dp
                            )
                    )
                    .padding(
                        16.dp
                    ),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                StatusBubble(
                    isCompleted =
                        challenge.isCompleted,
                    onClick =
                        onToggle
                )


                Spacer(
                    modifier =
                        Modifier.width(
                            14.dp
                        )
                )


                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.Top
                    ) {

                        Text(
                            text =
                                challenge.title,
                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,
                            fontWeight =
                                FontWeight.SemiBold,
                            color =
                                if (
                                    challenge.isCompleted
                                ) {
                                    Color(
                                        0xFF35543D
                                    )
                                } else {
                                    Color(
                                        0xFF1E2B21
                                    )
                                },
                            maxLines = 2,
                            overflow =
                                TextOverflow.Ellipsis,
                            modifier =
                                Modifier.weight(
                                    1f
                                )
                        )


                        Spacer(
                            modifier =
                                Modifier.width(
                                    8.dp
                                )
                        )


                        PointsChip(
                            points =
                                challenge.points,
                            isCompleted =
                                challenge.isCompleted
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(
                                7.dp
                            )
                    )


                    Text(
                        text =
                            challenge.description,
                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,
                        color =
                            Color(
                                0xFF718078
                            ),
                        maxLines = 3,
                        overflow =
                            TextOverflow.Ellipsis
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                13.dp
                            )
                    )


                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        CategoryChip(
                            category =
                                challenge.category
                        )


                        Spacer(
                            modifier =
                                Modifier.width(
                                    8.dp
                                )
                        )


                        StatusChip(
                            isCompleted =
                                challenge.isCompleted
                        )
                    }
                }
            }
        }


        AnimatedVisibility(
            visible =
                showPointsAnimation,
            modifier =
                Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .offset(
                        x = (-14).dp,
                        y = (-10).dp
                    ),
            enter =
                fadeIn() +
                        scaleIn(
                            initialScale = 0.7f
                        ) +
                        slideInVertically {
                            it / 2
                        },
            exit =
                fadeOut() +
                        scaleOut(
                            targetScale = 0.85f
                        ) +
                        slideOutVertically {
                            -it
                        }
        ) {

            Surface(
                shape =
                    RoundedCornerShape(
                        50
                    ),
                color =
                    Color(
                        0xFF2E9B58
                    ),
                shadowElevation =
                    5.dp
            ) {

                Text(
                    text =
                        "+${challenge.points} pts",
                    modifier =
                        Modifier.padding(
                            horizontal = 13.dp,
                            vertical = 7.dp
                        ),
                    color =
                        Color.White,
                    style =
                        MaterialTheme
                            .typography
                            .labelLarge,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}


@Composable
private fun StatusBubble(
    isCompleted: Boolean,
    onClick: () -> Unit
) {

    val scale by
    animateFloatAsState(
        targetValue =
            if (
                isCompleted
            ) {
                1.08f
            } else {
                1f
            },
        animationSpec =
            tween(
                220
            ),
        label =
            "checkScale"
    )


    val backgroundColor by
    animateColorAsState(
        targetValue =
            if (
                isCompleted
            ) {
                Color(
                    0xFF43A047
                )
            } else {
                Color(
                    0xFFF1F4F1
                )
            },
        animationSpec =
            tween(
                250
            ),
        label =
            "checkBackground"
    )


    val borderColor by
    animateColorAsState(
        targetValue =
            if (
                isCompleted
            ) {
                Color(
                    0xFF43A047
                )
            } else {
                Color(
                    0xFFCBD5CC
                )
            },
        animationSpec =
            tween(
                250
            ),
        label =
            "checkBorder"
    )


    Box(
        modifier = Modifier
            .size(
                44.dp
            )
            .scale(
                scale
            )
            .clip(
                CircleShape
            )
            .background(
                backgroundColor
            )
            .border(
                width = 1.5.dp,
                color =
                    borderColor,
                shape =
                    CircleShape
            )
            .clickable {
                onClick()
            },
        contentAlignment =
            Alignment.Center
    ) {

        Icon(
            imageVector =
                if (
                    isCompleted
                ) {
                    Icons.Default.Check
                } else {
                    Icons.Default.TaskAlt
                },
            contentDescription =
                if (
                    isCompleted
                ) {
                    "Marcar como pendiente"
                } else {
                    "Completar desafío"
                },
            tint =
                if (
                    isCompleted
                ) {
                    Color.White
                } else {
                    Color(
                        0xFF94A397
                    )
                },
            modifier =
                Modifier.size(
                    21.dp
                )
        )
    }
}


@Composable
private fun PointsChip(
    points: Int,
    isCompleted: Boolean
) {

    val backgroundColor =
        if (
            isCompleted
        ) {
            Color(
                0xFF43A047
            ).copy(
                alpha = 0.12f
            )
        } else {
            Color(
                0xFF147BC3
            ).copy(
                alpha = 0.10f
            )
        }


    val textColor =
        if (
            isCompleted
        ) {
            Color(
                0xFF2E7D32
            )
        } else {
            Color(
                0xFF147BC3
            )
        }


    Surface(
        shape =
            RoundedCornerShape(
                50
            ),
        color =
            backgroundColor
    ) {

        Text(
            text =
                if (
                    isCompleted
                ) {
                    "✓ $points pts"
                } else {
                    "+$points pts"
                },
            modifier =
                Modifier.padding(
                    horizontal = 11.dp,
                    vertical = 6.dp
                ),
            style =
                MaterialTheme
                    .typography
                    .labelLarge,
            fontWeight =
                FontWeight.SemiBold,
            color =
                textColor
        )
    }
}


@Composable
private fun StatusChip(
    isCompleted: Boolean
) {

    Surface(
        shape =
            RoundedCornerShape(
                50
            ),
        color =
            if (
                isCompleted
            ) {
                Color(
                    0xFF43A047
                ).copy(
                    alpha = 0.12f
                )
            } else {
                Color(
                    0xFFF1F3F1
                )
            }
    ) {

        Text(
            text =
                if (
                    isCompleted
                ) {
                    "Completado"
                } else {
                    "Pendiente"
                },
            modifier =
                Modifier.padding(
                    horizontal = 11.dp,
                    vertical = 6.dp
                ),
            style =
                MaterialTheme
                    .typography
                    .labelMedium,
            color =
                if (
                    isCompleted
                ) {
                    Color(
                        0xFF2E7D32
                    )
                } else {
                    Color(
                        0xFF67756B
                    )
                },
            fontWeight =
                FontWeight.Medium
        )
    }
}


@Composable
private fun CategoryChip(
    category: HabitCategory
) {

    val backgroundColor =
        when (
            category
        ) {

            HabitCategory.AGUA ->
                Color(
                    0xFF2196F3
                ).copy(
                    alpha = 0.10f
                )


            HabitCategory.ENERGIA ->
                Color(
                    0xFFFFB300
                ).copy(
                    alpha = 0.12f
                )


            HabitCategory.RESIDUOS ->
                Color(
                    0xFF43A047
                ).copy(
                    alpha = 0.11f
                )


            HabitCategory.MOVILIDAD ->
                Color(
                    0xFF7E57C2
                ).copy(
                    alpha = 0.10f
                )


            HabitCategory.GENERAL ->
                Color(
                    0xFF78909C
                ).copy(
                    alpha = 0.10f
                )
        }


    val textColor =
        when (
            category
        ) {

            HabitCategory.AGUA ->
                Color(
                    0xFF1976D2
                )


            HabitCategory.ENERGIA ->
                Color(
                    0xFFB77900
                )


            HabitCategory.RESIDUOS ->
                Color(
                    0xFF2E7D32
                )


            HabitCategory.MOVILIDAD ->
                Color(
                    0xFF6546A3
                )


            HabitCategory.GENERAL ->
                Color(
                    0xFF546E7A
                )
        }


    Surface(
        shape =
            RoundedCornerShape(
                50
            ),
        color =
            backgroundColor
    ) {

        Text(
            text =
                categoryName(
                    category
                ),
            modifier =
                Modifier.padding(
                    horizontal = 11.dp,
                    vertical = 6.dp
                ),
            style =
                MaterialTheme
                    .typography
                    .labelMedium,
            fontWeight =
                FontWeight.SemiBold,
            color =
                textColor
        )
    }
}


private fun categoryName(
    category: HabitCategory
): String {

    return when (
        category
    ) {

        HabitCategory.AGUA ->
            "AGUA"

        HabitCategory.ENERGIA ->
            "ENERGÍA"

        HabitCategory.RESIDUOS ->
            "RESIDUOS"

        HabitCategory.MOVILIDAD ->
            "MOVILIDAD"

        HabitCategory.GENERAL ->
            "GENERAL"
    }
}