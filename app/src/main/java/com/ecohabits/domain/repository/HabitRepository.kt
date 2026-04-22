package com.ecohabits.domain.repository

import com.ecohabits.domain.model.DailyChallenge
import com.ecohabits.domain.model.UserProgress

interface HabitRepository {
    suspend fun getDailyChallenges(): List<DailyChallenge>
    suspend fun getUserProgress(): UserProgress
    suspend fun completeChallenge(challengeId: String): UserProgress
}

