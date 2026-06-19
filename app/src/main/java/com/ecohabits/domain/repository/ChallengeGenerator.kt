package com.ecohabits.domain.repository

import com.ecohabits.domain.model.WeatherCondition
import com.ecohabits.domain.model.Challenge

interface ChallengeGenerator {
    /**
     * Genera una lista de retos basados en la ciudad y la condición climática.
     */
    suspend fun generateChallenges(city: String, weather: WeatherCondition): List<Challenge>
}
