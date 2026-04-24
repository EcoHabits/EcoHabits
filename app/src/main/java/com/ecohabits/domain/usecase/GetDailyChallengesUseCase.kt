package com.ecohabits.domain.usecase

import com.ecohabits.domain.model.WeatherChallenge
import com.ecohabits.domain.repository.HabitRepository
import javax.inject.Inject

class GetDailyChallengesUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(): List<WeatherChallenge> = repository.getDailyChallenges()
}

