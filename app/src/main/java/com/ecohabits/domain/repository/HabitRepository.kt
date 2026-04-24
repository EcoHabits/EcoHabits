package com.ecohabits.domain.repository

import com.ecohabits.domain.model.WeatherChallenge
import com.ecohabits.domain.model.UserProgress

interface HabitRepository {
    suspend fun getWeatherChallenges(): List<WeatherChallenge>
    suspend fun getUserProgress(): UserProgress
    suspend fun completeChallenge(challengeId: String): UserProgress
}

