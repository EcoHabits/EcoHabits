package com.ecohabits.domain.repository

import com.ecohabits.data.local.WeatherCondition
import com.ecohabits.domain.model.Challenge

interface ChallengeGenerator {
    /**
     * Genera una lista de retos basados en la condición climática.
     */
    suspend fun generateChallenges(weather: WeatherCondition): List<Challenge>
}
