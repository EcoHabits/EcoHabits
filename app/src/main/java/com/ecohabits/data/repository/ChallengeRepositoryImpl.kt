package com.ecohabits.data.repository

import com.ecohabits.data.mapper.toDomain
import com.ecohabits.data.mapper.toDto
import com.ecohabits.data.remote.dto.ChallengeDto
import com.ecohabits.data.remote.dto.UserChallengeDto
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.WeatherCondition
import com.ecohabits.domain.repository.ChallengeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChallengeRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : ChallengeRepository {

    override suspend fun fetchChallengesByContext(city: String, weather: WeatherCondition): List<Challenge> {
        return try {
            val results = supabase.postgrest["TblChallenges"]
                .select {
                    filter {
                        eq("CityName", city)
                        eq("WeatherCondition", weather.name)
                    }
                }
                .decodeList<ChallengeDto>()
            
            results.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun saveChallengesToGlobalCache(
        challenges: List<Challenge>,
        city: String,
        weather: WeatherCondition
    ) {
        try {
            val dtos = challenges.map { it.toDto(city, weather.name) }
            supabase.postgrest["TblChallenges"].insert(dtos)
        } catch (e: Exception) {
            // Log error
        }
    }

    override suspend fun assignChallengesToUser(userId: String, challenges: List<Challenge>) {
        try {
            val userChallenges = challenges.map { 
                UserChallengeDto(
                    idUserFK = userId,
                    idChallengeFK = it.id,
                    dateObtained = LocalDate.now().toString(),
                    isCompleted = false
                )
            }
            supabase.postgrest["TblChallengesUsers"].insert(userChallenges)
        } catch (e: Exception) {
            // Log error
        }
    }

    override suspend fun updateChallengeStatus(userId: String, challengeId: String, isCompleted: Boolean) {
        try {
            supabase.postgrest["TblChallengesUsers"].update(
                {
                    UserChallengeDto::isCompleted set isCompleted
                }
            ) {
                filter {
                    eq("IdUserFK", userId)
                    eq("IdChallengeFK", challengeId)
                }
            }
        } catch (e: Exception) {
            // Log error
        }
    }
}
