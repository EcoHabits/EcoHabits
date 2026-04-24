package com.ecohabits.data.repository

import com.ecohabits.domain.model.WeatherChallenge
import com.ecohabits.domain.model.UserProgress
import com.ecohabits.domain.repository.HabitRepository
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor() : HabitRepository {
    override suspend fun getWeatherChallenges(): List<WeatherChallenge> = TODO("Skeleton only")

    override suspend fun getUserProgress(): UserProgress = TODO("Skeleton only")

    override suspend fun completeChallenge(challengeId: String): UserProgress = TODO("Skeleton only")
}

