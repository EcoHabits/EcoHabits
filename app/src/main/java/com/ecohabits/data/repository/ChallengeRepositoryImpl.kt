package com.ecohabits.data.repository

import android.util.Log
import com.ecohabits.data.local.dao.ChallengeDao
import com.ecohabits.data.mapper.toDomain
import com.ecohabits.data.mapper.toDto
import com.ecohabits.data.mapper.toEntity
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
    private val supabase: SupabaseClient,
    private val challengeDao: ChallengeDao
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
            
            val challenges = results.map { it.toDomain() }
            
            // Cachear localmente
            if (challenges.isNotEmpty()) {
                challengeDao.deleteAllChallenges()
                challengeDao.upsertChallenges(challenges.map { it.toEntity() })
            }
            
            challenges
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error fetching from Supabase, loading local cache: ${e.message}")
            // Fallback a Room
            challengeDao.getAllChallenges().map { it.toDomain() }
        }
    }

    override suspend fun getUserChallenges(userId: String): List<Challenge> {
        return try {
            val userAssignments = supabase.postgrest["TblChallengesUsers"]
                .select {
                    filter {
                        eq("IdUserFK", userId)
                    }
                }
                .decodeList<UserChallengeDto>()
            
            if (userAssignments.isEmpty()) {
                 return challengeDao.getAllChallenges().map { it.toDomain() }
            }

            val assignmentsMap = userAssignments.associateBy { it.idChallengeFK }
            val challengeIds = userAssignments.map { it.idChallengeFK }

            val challengeDetails = supabase.postgrest["TblChallenges"]
                .select {
                    filter {
                        isIn("IdChallenge", challengeIds)
                    }
                }
                .decodeList<ChallengeDto>()

            val challenges = challengeDetails.map { dto ->
                dto.toDomain().copy(
                    isCompleted = assignmentsMap[dto.idChallenge]?.isCompleted ?: false
                )
            }
            
            // Actualizar cache local con los retos del usuario
            if (challenges.isNotEmpty()) {
                challengeDao.deleteAllChallenges()
                challengeDao.upsertChallenges(challenges.map { it.toEntity() })
            }
            
            challenges
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error fetching user challenges, loading local cache: ${e.message}")
            challengeDao.getAllChallenges().map { it.toDomain() }
        }
    }

    override suspend fun saveChallenges(
        challenges: List<Challenge>,
        city: String,
        weather: WeatherCondition?
    ): List<Challenge> {
        return try {
            val dtos = challenges.map { it.toDto(city, weather?.name ?: WeatherCondition.UNKNOWN.name) }
            
            val insertedDtos = supabase.postgrest["TblChallenges"].insert(dtos) {
                select() 
            }.decodeList<ChallengeDto>()

            val savedChallenges = insertedDtos.map { it.toDomain() }
            
            // Guardar también en Room
            if (savedChallenges.isNotEmpty()) {
                challengeDao.upsertChallenges(savedChallenges.map { it.toEntity() })
            }

            savedChallenges
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error saving challenges: ${e.message}")
            emptyList()
        }
    }

    override suspend fun assignChallengesToUser(userId: String, challenges: List<Challenge>) {
        try {
            val existingAssignments = supabase.postgrest["TblChallengesUsers"]
                .select {
                    filter {
                        eq("IdUserFK", userId)
                    }
                }
                .decodeList<UserChallengeDto>()
            
            val assignedChallengeIds = existingAssignments.map { it.idChallengeFK }.toSet()
            val newChallenges = challenges.filter { it.id !in assignedChallengeIds }

            if (newChallenges.isEmpty()) {
                return
            }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val userChallenges = newChallenges.map { 
                UserChallengeDto(
                    idUserFK = userId,
                    idChallengeFK = it.id,
                    dateObtained = currentDate,
                    isCompleted = false
                )
            }
            supabase.postgrest["TblChallengesUsers"].insert(userChallenges)
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error assigning challenges: ${e.message}")
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
            Log.e("ChallengeRepo", "Error updating status: ${e.message}")
        }
    }
}
