package com.ecohabits.domain.repository

import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.UserProgress

interface HabitRepository {
    suspend fun getWeatherChallenges(): List<Challenge>
    suspend fun getUserProgress(): UserProgress
    suspend fun completeChallenge(challengeId: String): UserProgress
}

