package com.ecohabits.domain.repository

import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.WeatherCondition

interface ChallengeRepository {

    /**
     * Busca retos en la caché global de Supabase
     * filtrando por ciudad y clima.
     */
    suspend fun fetchChallengesByContext(
        city: String,
        weather: WeatherCondition?
    ): List<Challenge>


    /**
     * Obtiene los retos asignados a un usuario específico,
     * incluyendo su estado de completado.
     */
    suspend fun getUserChallenges(
        userId: String
    ): List<Challenge>


    /**
     * Guarda retos en la caché global de Supabase
     * y devuelve la lista con los IDs generados.
     */
    suspend fun saveChallenges(
        challenges: List<Challenge>,
        city: String,
        weather: WeatherCondition?
    ): List<Challenge>


    /**
     * Vincula retos específicos a un usuario.
     */
    suspend fun assignChallengesToUser(
        userId: String,
        challenges: List<Challenge>
    )


    /**
     * Actualiza el estado de completado de un reto.
     */
    suspend fun updateChallengeStatus(
        userId: String,
        challengeId: String,
        isCompleted: Boolean
    )


    /**
     * Devuelve las fechas reales en las que
     * el usuario completó retos.
     *
     * Estas fechas se obtienen desde CompletedAt
     * de TblChallengesUsers.
     */
    suspend fun getUserCompletionDates(
        userId: String
    ): List<String>
}