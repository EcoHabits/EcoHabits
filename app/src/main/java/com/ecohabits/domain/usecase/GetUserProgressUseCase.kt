package com.ecohabits.domain.usecase

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
            val userId = authRepository.getCurrentUserId()
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val challenges = challengeRepository.getUserChallenges(userId)
            val completedChallenges = challenges.filter { it.isCompleted }
            val totalPoints = completedChallenges.sumOf { it.points }

            Result.success(
                UserProgress(
                    points = totalPoints,
                    level = calculateLevel(totalPoints),
                    metrics = ImpactMetrics(
                        waterSavedLiters = 0.0,
                        co2ReducedKg = 0.0,
                        wasteReducedKg = 0.0
                    ),
                    badges = emptyList()
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun calculateLevel(points: Int): Int = (points / POINTS_PER_LEVEL) + 1

    private companion object {
        const val POINTS_PER_LEVEL = 500
    }
}