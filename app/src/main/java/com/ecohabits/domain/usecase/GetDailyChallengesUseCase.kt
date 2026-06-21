package com.ecohabits.domain.usecase

import android.util.Log
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.ChallengeGenerator
import com.ecohabits.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetDailyChallengesUseCase @Inject constructor(
    private val getCurrentContextUseCase: GetCurrentContextUseCase,
    private val challengeRepository: ChallengeRepository,
    private val challengeGenerator: ChallengeGenerator,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<List<Challenge>> {
        Log.d("DailyChallenges", "Iniciando orquestación de retos...")
        return try {
            val context = getCurrentContextUseCase() 
            if (context == null) {
                Log.e("DailyChallenges", "No se pudo obtener el contexto")
                return Result.failure(Exception("No se pudo obtener el contexto (revisa GPS)"))
            }
            
            val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                Log.e("DailyChallenges", "Usuario no autenticado")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            Log.d("DailyChallenges", "Buscando caché en Supabase para ${context.cityName}...")
            var challenges = challengeRepository.fetchChallengesByContext(context.cityName, context.weatherCondition)

            if (challenges.isEmpty()) {
                Log.d("DailyChallenges", "No hay caché. Llamando a OpenRouter...")
                val generatedChallenges = challengeGenerator.generateChallenges(context.cityName, context.weatherCondition)
                
                if (generatedChallenges.isNotEmpty()) {
                    Log.d("DailyChallenges", "Retos generados: ${generatedChallenges.size}. Guardando en Supabase...")
                    // Guardamos y recuperamos los retos con sus IDs reales (UUID) asignados por Supabase
                    challenges = challengeRepository.saveAndFetchChallenges(generatedChallenges, context.cityName, context.weatherCondition)
                } else {
                    Log.w("DailyChallenges", "OpenRouter no devolvió retos o hubo error en generación")
                }
            } else {
                Log.d("DailyChallenges", "Retos obtenidos de la caché de Supabase: ${challenges.size}")
            }

            if (challenges.isNotEmpty()) {
                Log.d("DailyChallenges", "Asignando ${challenges.size} retos al usuario $userId...")
                challengeRepository.assignChallengesToUser(userId, challenges)
            } else {
                Log.w("DailyChallenges", "No se pudieron obtener retos de ninguna fuente")
            }

            Result.success(challenges)
        } catch (e: Exception) {
            Log.e("DailyChallenges", "Error crítico en orquestador: ${e.message}")
            Result.failure(e)
        }
    }
}
