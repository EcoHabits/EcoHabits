package com.ecohabits.data.remote.ai

import com.ecohabits.data.local.WeatherCondition
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.repository.ChallengeGenerator
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiChallengeGenerator @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val gson: Gson
) : ChallengeGenerator {

    override suspend fun generateChallenges(weather: WeatherCondition): List<Challenge> {
        val prompt = """
            Actúa como un experto en sostenibilidad y ecología urbana. 
            El clima actual es $weather.
            Genera exactamente 3 retos ecológicos diarios que sean prácticos y relevantes para este clima.
            
            Responde EXCLUSIVAMENTE en formato JSON con la siguiente estructura:
            {
              "challenges": [
                {
                  "title": "Título corto del reto",
                  "description": "Descripción detallada",
                  "points": 50,
                  "category": "AGUA" 
                }
              ]
            }
            
            Las categorías válidas son: AGUA, ENERGIA, RESIDUOS, MOVILIDAD.
            El idioma debe ser Español. No incluyas explicaciones fuera del JSON.
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(prompt)
            val jsonString = response.text?.replace("```json", "")?.replace("```", "")?.trim()
            
            val dto = gson.fromJson(jsonString, GeminiResponseDto::class.java)
            
            dto.challenges.mapIndexed { index, item ->
                Challenge(
                    idChallenge = index, // Opcional, Room generará uno real luego
                    titleChallenge = item.title,
                    descriptionChallenge = item.description,
                    challengeStatus = false,
                    pointsChallenge = item.points
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
