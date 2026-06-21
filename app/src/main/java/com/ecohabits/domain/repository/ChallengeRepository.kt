package com.ecohabits.domain.repository

import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.WeatherCondition

interface ChallengeRepository {
    /**
     * Busca retos en la caché global de Supabase filtrando por ciudad y clima.
     */
    suspend fun fetchChallengesByContext(city: String, weather: WeatherCondition?): List<Challenge>

    /**
     * Guarda retos recién generados en la caché global de Supabase.
     */
    suspend fun saveChallengesToGlobalCache(challenges: List<Challenge>, city: String, weather: WeatherCondition?)

    /**
     * Guarda retos en la caché y devuelve la lista con los IDs (UUID) generados por la base de datos.
     */
    suspend fun saveAndFetchChallenges(challenges: List<Challenge>, city: String, weather: WeatherCondition?): List<Challenge>

    /**
     * Vincula retos específicos a un usuario y gestiona su estado de completado.
     */
    suspend fun assignChallengesToUser(userId: String, challenges: List<Challenge>)

    suspend fun updateChallengeStatus(userId: String, challengeId: String, isCompleted: Boolean)
}
