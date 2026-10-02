package com.ecohabits.presentation.startup

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin


@Composable
fun LiquidProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "liquidProgress"
        )


    val waveOffset by
    infiniteTransition.animateFloat(

        initialValue = 0f,

        targetValue =
            (2 * PI).toFloat(),

        animationSpec =
            infiniteRepeatable(

                animation =
                    tween(
                        durationMillis = 1800,
                        easing = LinearEasing
                    )
            ),

        label =
            "liquidWave"
    )


    val safeProgress =
        progress.coerceIn(
            0f,
            1f
        )


    Canvas(

        modifier =
            modifier
                .fillMaxWidth()
                .height(22.dp)
                .background(
                    color =
                        Color(0xFFE7F3EA),
                    shape =
                        RoundedCornerShape(
                            50.dp
                        )
                )

    ) {

        val cornerRadius =
            size.height / 2f


        val clippingPath =
            Path().apply {

                addRoundRect(
                    androidx.compose.ui.geometry.RoundRect(
                        rect =
                            androidx.compose.ui.geometry.Rect(
                                offset =
                                    Offset.Zero,

                                size =
                                    Size(
                                        size.width,
                                        size.height
                                    )
                            ),

                        cornerRadius =
                            androidx.compose.ui.geometry.CornerRadius(
                                cornerRadius,
                                cornerRadius
                            )
                    )
                )
            }


        clipPath(
            clippingPath
        ) {

            val progressWidth =
                size.width *
                        safeProgress


            /*
             * Fondo del líquido.
             */
            drawRect(

                color =
                    Color(
                        0xFF2F9E64
                    ),

                topLeft =
                    Offset.Zero,

                size =
                    Size(
                        progressWidth,
                        size.height
                    )
            )


            /*
             * Onda líquida.
             */
            if (progressWidth > 0f) {

                val wavePath =
                    Path()


                val amplitude =
                    size.height *
                            0.17f


                val centerY =
                    size.height *
                            0.36f


                wavePath.moveTo(
                    0f,
                    centerY
                )


                var x =
                    0f


                while (
                    x <= progressWidth
                ) {

                    val angle =
                        (
                                x /
                                        size.width *
                                        PI *
                                        4
                                ).toFloat() +
                                waveOffset


                    val y =
                        centerY +
                                sin(
                                    angle
                                ) *
                                amplitude


                    wavePath.lineTo(
                        x,
                        y
                    )


                    x += 6f
                }


                wavePath.lineTo(
                    progressWidth,
                    size.height
                )


                wavePath.lineTo(
                    0f,
                    size.height
                )


                wavePath.close()


                drawPath(

                    path =
                        wavePath,

                    color =
                        Color(
                            0xFF53BE73
                        )
                )
            }


            /*
             * Brillo superior.
             */
            drawRect(

                color =
                    Color.White.copy(
                        alpha = 0.18f
                    ),

                topLeft =
                    Offset.Zero,

                size =
                    Size(
                        progressWidth,
                        size.height *
                                0.25f
                    )
            )
        }
    }
}