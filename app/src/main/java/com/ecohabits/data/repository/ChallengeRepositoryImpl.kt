package com.ecohabits.data.repository

import android.util.Log
import com.ecohabits.data.mapper.toDomain
import com.ecohabits.data.mapper.toDto
import com.ecohabits.data.remote.dto.ChallengeDto
import com.ecohabits.data.remote.dto.UserChallengeDto
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.WeatherCondition
import com.ecohabits.domain.repository.ChallengeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChallengeRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : ChallengeRepository {

    override suspend fun fetchChallengesByContext(city: String, weather: WeatherCondition?): List<Challenge> {
        return try {
            val results = supabase.postgrest["TblChallenges"]
                .select {
                    filter {
                        eq("CityName", city)
                        weather?.let { eq("WeatherCondition", it.name) }
                    }
                }
                .decodeList<ChallengeDto>()
            
            results.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error fetching challenges: ${e.message}")
            emptyList()
        }
    }

    override suspend fun saveChallengesToGlobalCache(
        challenges: List<Challenge>,
        city: String,
        weather: WeatherCondition?
    ) {
        try {
            val dtos = challenges.map { it.toDto(city, weather?.name ?: WeatherCondition.UNKNOWN.name) }
            supabase.postgrest["TblChallenges"].insert(dtos)
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error saving challenges to cache: ${e.message}")
        }
    }

    override suspend fun saveAndFetchChallenges(
        challenges: List<Challenge>,
        city: String,
        weather: WeatherCondition?
    ): List<Challenge> {
        return try {
            val dtos = challenges.map { it.toDto(city, weather?.name ?: WeatherCondition.UNKNOWN.name) }
            
            // Insertamos y pedimos de vuelta los objetos con sus IDs generados por Supabase
            val insertedDtos = supabase.postgrest["TblChallenges"].insert(dtos) {
                select() 
            }.decodeList<ChallengeDto>()

            insertedDtos.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error saving and fetching challenges: ${e.message}")
            emptyList()
        }
    }

    override suspend fun assignChallengesToUser(userId: String, challenges: List<Challenge>) {
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val userChallenges = challenges.map { 
                UserChallengeDto(
                    idUserFK = userId,
                    idChallengeFK = it.id,
                    dateObtained = currentDate,
                    isCompleted = false
                )
            }
            supabase.postgrest["TblChallengesUsers"].insert(userChallenges)
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error assigning challenges to user: ${e.message}")
        }
    }

    override suspend fun updateChallengeStatus(userId: String, challengeId: String, isCompleted: Boolean) {
        try {
            supabase.postgrest["TblChallengesUsers"].update(
                {
                    set("IsCompleted", isCompleted)
                }
            ) {
                filter {
                    eq("IdUserFK", userId)
                    eq("IdChallengeFK", challengeId)
                }
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error updating challenge status: ${e.message}")
        }
    }
}
