package com.ecohabits.domain.usecase

import com.ecohabits.domain.model.HabitCategory
import com.ecohabits.domain.model.ImpactMetrics
import com.ecohabits.domain.model.UserProgress
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetUserProgressUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Result<UserProgress> {
        return try {

            val userId =
                authRepository.getCurrentUserId()
                    ?: return Result.failure(
                        Exception("Usuario no autenticado")
                    )

            val challenges =
                challengeRepository.getUserChallenges(
                    userId
                )

            val completedChallenges =
                challenges.filter {
                    it.isCompleted
                }

            val totalPoints =
                completedChallenges.sumOf {
                    it.points
                }

            var waterSavedLiters = 0.0
            var co2ReducedKg = 0.0
            var wasteReducedKg = 0.0

            completedChallenges.forEach { challenge ->

                when (challenge.category) {

                    HabitCategory.AGUA -> {

                        waterSavedLiters +=
                            WATER_LITERS_PER_CHALLENGE
                    }

                    HabitCategory.ENERGIA -> {

                        co2ReducedKg +=
                            ENERGY_CO2_KG_PER_CHALLENGE
                    }

                    HabitCategory.RESIDUOS -> {

                        wasteReducedKg +=
                            WASTE_KG_PER_CHALLENGE
                    }

                    HabitCategory.MOVILIDAD -> {

                        co2ReducedKg +=
                            MOBILITY_CO2_KG_PER_CHALLENGE
                    }

                    HabitCategory.GENERAL -> {

                        // No sumamos impacto específico
                        // en esta primera versión.
                    }
                }
            }

            Result.success(
                UserProgress(
                    points = totalPoints,

                    level =
                        calculateLevel(
                            totalPoints
                        ),

                    metrics =
                        ImpactMetrics(
                            waterSavedLiters =
                                waterSavedLiters,

                            co2ReducedKg =
                                co2ReducedKg,

                            wasteReducedKg =
                                wasteReducedKg
                        ),

                    badges =
                        emptyList()
                )
            )

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    private fun calculateLevel(
        points: Int
    ): Int {

        return (
                points /
                        POINTS_PER_LEVEL
                ) + 1
    }

    private companion object {

        const val POINTS_PER_LEVEL = 500

        const val WATER_LITERS_PER_CHALLENGE =
            10.0

        const val ENERGY_CO2_KG_PER_CHALLENGE =
            0.5

        const val WASTE_KG_PER_CHALLENGE =
            0.3

        const val MOBILITY_CO2_KG_PER_CHALLENGE =
            1.2
    }
}