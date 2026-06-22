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

    override suspend fun getUserChallenges(userId: String): List<Challenge> {
        return try {
            // 1. Obtenemos la relación de retos del usuario (incluyendo el estado IsCompleted)
            val userAssignments = supabase.postgrest["TblChallengesUsers"]
                .select {
                    filter {
                        eq("IdUserFK", userId)
                    }
                }
                .decodeList<UserChallengeDto>()
            
            if (userAssignments.isEmpty()) return emptyList()

            val assignmentsMap = userAssignments.associateBy { it.idChallengeFK }
            val challengeIds = userAssignments.map { it.idChallengeFK }

            // 2. Obtenemos los detalles de esos retos desde la tabla maestra
            // Usamos 'in' para traer todos los retos de una vez
            val challengeDetails = supabase.postgrest["TblChallenges"]
                .select {
                    filter {
                        isIn("IdChallenge", challengeIds)
                    }
                }
                .decodeList<ChallengeDto>()

            // 3. Mapeamos a dominio combinando la información
            challengeDetails.map { dto ->
                dto.toDomain().copy(
                    isCompleted = assignmentsMap[dto.idChallenge]?.isCompleted ?: false
                )
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error fetching user challenges: ${e.message}")
            emptyList()
        }
    }

    override suspend fun saveChallenges(
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

            Log.d("ChallengeRepo", "Se guardaron ${insertedDtos.size} retos en la caché global")
            insertedDtos.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error saving challenges: ${e.message}")
            emptyList()
        }
    }

    override suspend fun assignChallengesToUser(userId: String, challenges: List<Challenge>) {
        try {
            // IDs de los retos que el usuario ya tiene asignados
            val existingAssignments = supabase.postgrest["TblChallengesUsers"]
                .select {
                    filter {
                        eq("IdUserFK", userId)
                    }
                }
                .decodeList<UserChallengeDto>()
            
            val assignedChallengeIds = existingAssignments.map { it.idChallengeFK }.toSet()

            // Quedarnos con los que NO estan asignados
            val newChallenges = challenges.filter { it.id !in assignedChallengeIds }

            if (newChallenges.isEmpty()) {
                Log.d("ChallengeRepo", "No hay nuevos retos para asignar al usuario $userId")
                return
            }

            // se insertan los nuevos retos
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
            Log.d("ChallengeRepo", "Se asignaron ${newChallenges.size} nuevos retos al usuario $userId")
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
