package com.ecohabits.domain.usecase

import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.ChallengeRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class GetUserStreakUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Result<Int> {

        return try {

            val userId =
                authRepository.getCurrentUserId()
                    ?: return Result.failure(
                        Exception("Usuario no autenticado")
                    )


            val completionDates =
                challengeRepository
                    .getUserCompletionDates(userId)


            val streak =
                calculateStreak(
                    completionDates
                )


            Result.success(streak)


        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    private fun calculateStreak(
        completedAtValues: List<String>
    ): Int {

        if (completedAtValues.isEmpty()) {
            return 0
        }

        val parser =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.US
            )


        val completionDays =
            completedAtValues
                .mapNotNull { value ->

                    try {

                        val parsedDate =
                            parser.parse(value)

                        parsedDate?.let {
                            normalizeDay(it)
                        }

                    } catch (e: Exception) {

                        null
                    }
                }
                .toSet()


        if (completionDays.isEmpty()) {
            return 0
        }


        val today =
            normalizeDay(
                Date()
            )


        val yesterdayCalendar =
            Calendar.getInstance().apply {

                time = today

                add(
                    Calendar.DAY_OF_YEAR,
                    -1
                )
            }


        val yesterday =
            yesterdayCalendar.time


        /*
         * Regla
         * Si completó algo hoy, contamos desde hoy. Si todavía no completó nada hoy,
         * pero sí completó ayer, mantenemos su racha. Si no tiene actividad ni hoy ni ayer, la racha actual es 0.
         */
        var currentDay =
            when {

                completionDays.contains(today) -> {
                    today
                }

                completionDays.contains(yesterday) -> {
                    yesterday
                }

                else -> {
                    return 0
                }
            }


        var streak = 0


        while (
            completionDays.contains(
                currentDay
            )
        ) {

            streak++


            val calendar =
                Calendar.getInstance().apply {

                    time = currentDay

                    add(
                        Calendar.DAY_OF_YEAR,
                        -1
                    )
                }


            currentDay =
                calendar.time
        }


        return streak
    }


    /**
     * Convierte cualquier fecha/hora al comienzo de ese día en la zona horaria local Así varios retos completados el mismo día cuentan solamente como 1 día de racha.
     */
    private fun normalizeDay(
        date: Date
    ): Date {

        return Calendar
            .getInstance()
            .apply {

                time = date

                set(
                    Calendar.HOUR_OF_DAY,
                    0
                )

                set(
                    Calendar.MINUTE,
                    0
                )

                set(
                    Calendar.SECOND,
                    0
                )

                set(
                    Calendar.MILLISECOND,
                    0
                )
            }
            .time
    }
}