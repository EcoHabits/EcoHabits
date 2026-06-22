package com.ecohabits.domain.usecase

import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.ChallengeRepository
import javax.inject.Inject

/**
 * Use case para marcar un reto como completado.
 */
class CompleteChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(challengeId: String, isCompleted: Boolean = true): Result<Unit> {
        return try {
            val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                return Result.failure(Exception("Usuario no autenticado"))
            }

            challengeRepository.updateChallengeStatus(userId, challengeId, isCompleted)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
