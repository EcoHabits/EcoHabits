package com.ecohabits.domain.usecase

import com.ecohabits.domain.model.UserProgress
import com.ecohabits.domain.repository.HabitRepository
import javax.inject.Inject

class CompleteChallengeUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(challengeId: String): UserProgress {
        return repository.completeChallenge(challengeId)
    }
}

