package com.ecohabits.presentation.startup

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ecohabits.R


@Composable
fun SplashScreen(
    uiState: StartupUiState
) {

    val animatedProgress by animateFloatAsState(
        targetValue = uiState.progress,
        animationSpec = tween(
            durationMillis = 450
        ),
        label = "startupProgress"
    )

    val logoScale by animateFloatAsState(
        targetValue =
            if (uiState.progress > 0f) {
                1f
            } else {
                0.85f
            },
        animationSpec = tween(
            durationMillis = 600
        ),
        label = "logoScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFF0F8F3),
                        Color(0xFFE7F4ED)
                    )
                )
            )
    ) {

        LiquidSplashBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 34.dp
                ),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.ecohabits_logo
                ),
                contentDescription = "EcoHabits",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(245.dp)
                    .scale(logoScale)
            )

            Spacer(
                modifier = Modifier.height(
                    38.dp
                )
            )

            Text(
                text = "${uiState.percentage}%",
                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Color(0xFF146B40)
            )

            Spacer(
                modifier = Modifier.height(
                    14.dp
                )
            )

            LiquidProgressBar(
                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(
                    18.dp
                )
            )

            Text(
                text = uiState.message,
                style =
                    MaterialTheme
                        .typography
                        .bodyLarge,
                color =
                    Color(0xFF456158),
                textAlign =
                    TextAlign.Center,
                modifier =
                    Modifier.alpha(
                        0.9f
                    )
            )
        }
    }
}


@Composable
private fun LiquidSplashBackground() {

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "splashLiquid"
        )

    val movement by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 5500
                    ),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label = "movement"
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        drawCircle(
            color =
                Color(0xFF4CAF50).copy(
                    alpha = 0.10f
                ),
            radius =
                size.width * 0.58f,
            center =
                Offset(
                    x =
                        size.width *
                                (
                                        -0.08f +
                                                movement *
                                                0.12f
                                        ),
                    y =
                        size.height *
                                0.12f
                )
        )

        drawCircle(
            color =
                Color(0xFF147BC3).copy(
                    alpha = 0.10f
                ),
            radius =
                size.width * 0.48f,
            center =
                Offset(
                    x =
                        size.width *
                                (
                                        0.95f -
                                                movement *
                                                0.08f
                                        ),
                    y =
                        size.height *
                                0.86f
                )
        )
    }
}