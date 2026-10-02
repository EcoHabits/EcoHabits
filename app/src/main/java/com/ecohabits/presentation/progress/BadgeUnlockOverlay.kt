package com.ecohabits.presentation.progress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun BadgeUnlockOverlay(
    badge: BadgeUiState,
    onDismiss: () -> Unit
) {

    val backgroundAlpha =
        remember(
            badge.id
        ) {
            Animatable(0f)
        }

    val badgeScale =
        remember(
            badge.id
        ) {
            Animatable(0f)
        }

    val badgeRotation =
        remember(
            badge.id
        ) {
            Animatable(-18f)
        }

    val contentAlpha =
        remember(
            badge.id
        ) {
            Animatable(0f)
        }

    val glowScale =
        remember(
            badge.id
        ) {
            Animatable(0.4f)
        }


    LaunchedEffect(
        badge.id
    ) {

        launch {

            backgroundAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        }


        launch {

            glowScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio =
                        Spring.DampingRatioLowBouncy,
                    stiffness =
                        Spring.StiffnessLow
                )
            )
        }


        badgeScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio =
                    Spring.DampingRatioMediumBouncy,
                stiffness =
                    Spring.StiffnessLow
            )
        )


        badgeRotation.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio =
                    Spring.DampingRatioMediumBouncy,
                stiffness =
                    Spring.StiffnessMedium
            )
        )


        delay(
            120
        )


        contentAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 350
            )
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(
                    alpha =
                        0.60f *
                                backgroundAlpha.value
                )
            ),
        contentAlignment =
            Alignment.Center
    ) {


        LiquidBackground(
            badgeColor =
                badge.backgroundColor,
            alpha =
                backgroundAlpha.value
        )


        SparkleLayer(
            badgeColor =
                badge.backgroundColor,
            alpha =
                backgroundAlpha.value
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 28.dp
                ),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {


            Box(
                contentAlignment =
                    Alignment.Center
            ) {


                /*
                 * Glow exterior.
                 */
                Box(
                    modifier = Modifier
                        .size(
                            215.dp
                        )
                        .scale(
                            glowScale.value
                        )
                        .background(
                            badge
                                .backgroundColor
                                .copy(
                                    alpha = 0.13f
                                ),
                            CircleShape
                        )
                )


                /*
                 * Glow intermedio.
                 */
                Box(
                    modifier = Modifier
                        .size(
                            178.dp
                        )
                        .scale(
                            glowScale.value
                        )
                        .background(
                            Color.White.copy(
                                alpha = 0.09f
                            ),
                            CircleShape
                        )
                )


                /*
                 * Medalla.
                 */
                Box(
                    modifier = Modifier
                        .size(
                            140.dp
                        )
                        .scale(
                            badgeScale.value
                        )
                        .graphicsLayer {

                            rotationZ =
                                badgeRotation.value

                            shadowElevation =
                                30f
                        }
                        .background(
                            badge.backgroundColor,
                            CircleShape
                        ),
                    contentAlignment =
                        Alignment.Center
                ) {


                    Icon(
                        imageVector =
                            badge.icon,
                        contentDescription =
                            badge.title,
                        modifier = Modifier
                            .size(
                                78.dp
                            ),
                        tint =
                            Color.White
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(
                        28.dp
                    )
            )


            Column(
                modifier =
                    Modifier.alpha(
                        contentAlpha.value
                    ),
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {


                Text(
                    text =
                        "¡Nueva insignia!",
                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color.White,
                    textAlign =
                        TextAlign.Center
                )


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
                            .headlineMedium,
                    fontWeight =
                        FontWeight.ExtraBold,
                    color =
                        Color.White,
                    textAlign =
                        TextAlign.Center
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            8.dp
                        )
                )


                Text(
                    text =
                        badge.description,
                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,
                    color =
                        Color.White.copy(
                            alpha = 0.90f
                        ),
                    textAlign =
                        TextAlign.Center
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            28.dp
                        )
                )


                Button(
                    onClick =
                        onDismiss,
                    shape =
                        RoundedCornerShape(
                            24.dp
                        ),
                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    Color.White,
                                contentColor =
                                    badge.backgroundColor
                            )
                ) {


                    Text(
                        text =
                            "Continuar",
                        fontWeight =
                            FontWeight.Bold,
                        modifier =
                            Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 2.dp
                            )
                    )
                }
            }
        }
    }
}


@Composable
private fun LiquidBackground(
    badgeColor: Color,
    alpha: Float
) {

    val infiniteTransition =
        rememberInfiniteTransition(
            label =
                "liquidBackground"
        )


    val movement1 by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 4800,
                        easing =
                            FastOutSlowInEasing
                    ),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label =
            "liquidMovement1"
    )


    val movement2 by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 6500,
                        easing =
                            FastOutSlowInEasing
                    ),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label =
            "liquidMovement2"
    )


    val movement3 by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 3900,
                        easing =
                            FastOutSlowInEasing
                    ),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label =
            "liquidMovement3"
    )


    Canvas(
        modifier =
            Modifier.fillMaxSize()
    ) {

        val w =
            size.width

        val h =
            size.height


        /*
         * Blob superior izquierdo.
         */
        drawCircle(

            color =
                badgeColor.copy(
                    alpha =
                        0.34f * alpha
                ),

            radius =
                w * 0.43f,

            center =
                Offset(

                    x =
                        w *
                                (
                                        -0.03f +
                                                movement1 *
                                                0.15f
                                        ),

                    y =
                        h *
                                (
                                        0.10f +
                                                movement2 *
                                                0.06f
                                        )
                )
        )


        /*
         * Blob azul inferior derecho.
         */
        drawCircle(

            color =
                Color(
                    0xFF4B8EFF
                ).copy(
                    alpha =
                        0.30f * alpha
                ),

            radius =
                w * 0.44f,

            center =
                Offset(

                    x =
                        w *
                                (
                                        0.92f -
                                                movement2 *
                                                0.10f
                                        ),

                    y =
                        h *
                                (
                                        0.78f -
                                                movement1 *
                                                0.05f
                                        )
                )
        )


        /*
         * Blob central translúcido.
         */
        drawCircle(

            color =
                Color.White.copy(
                    alpha =
                        0.10f * alpha
                ),

            radius =
                w * 0.26f,

            center =
                Offset(

                    x =
                        w *
                                (
                                        0.70f +
                                                movement3 *
                                                0.05f
                                        ),

                    y =
                        h *
                                (
                                        0.25f +
                                                movement1 *
                                                0.05f
                                        )
                )
        )


        /*
         * Blob inferior verde claro.
         */
        drawCircle(

            color =
                Color(
                    0xFF75E6B4
                ).copy(
                    alpha =
                        0.18f * alpha
                ),

            radius =
                w * 0.30f,

            center =
                Offset(

                    x =
                        w *
                                (
                                        0.18f +
                                                movement2 *
                                                0.12f
                                        ),

                    y =
                        h *
                                (
                                        0.92f -
                                                movement3 *
                                                0.08f
                                        )
                )
        )
    }
}


@Composable
private fun SparkleLayer(
    badgeColor: Color,
    alpha: Float
) {

    val transition =
        rememberInfiniteTransition(
            label =
                "sparkles"
        )


    val movement by
    transition.animateFloat(
        initialValue = 0f,
        targetValue =
            (Math.PI * 2).toFloat(),
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis =
                            5200
                    )
            ),
        label =
            "sparkleMovement"
    )


    Canvas(
        modifier =
            Modifier.fillMaxSize()
    ) {

        val center =
            Offset(
                x =
                    size.width / 2f,
                y =
                    size.height * 0.40f
            )


        repeat(
            12
        ) { index ->

            val angle =
                movement +
                        index *
                        (
                                Math.PI * 2 / 12
                                ).toFloat()


            val radius =
                size.width *
                        (
                                0.25f +
                                        (
                                                index % 3
                                                ) *
                                        0.035f
                                )


            val x =
                center.x +
                        cos(
                            angle
                        ) *
                        radius


            val y =
                center.y +
                        sin(
                            angle
                        ) *
                        radius


            val particleRadius =
                if (
                    index % 3 == 0
                ) {

                    6f

                } else {

                    3.5f
                }


            drawCircle(
                color =
                    if (
                        index % 2 == 0
                    ) {

                        Color.White.copy(
                            alpha =
                                0.60f * alpha
                        )

                    } else {

                        badgeColor.copy(
                            alpha =
                                0.55f * alpha
                        )
                    },
                radius =
                    particleRadius,
                center =
                    Offset(
                        x = x,
                        y = y
                    )
            )
        }
    }
}