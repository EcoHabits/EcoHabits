package com.ecohabits.data.remote.ai

import android.util.Log
import com.ecohabits.BuildConfig
import com.ecohabits.data.remote.AiApi
import com.ecohabits.data.remote.dto.*
import com.ecohabits.domain.model.HabitCategory
import com.ecohabits.domain.model.WeatherCondition
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.repository.ChallengeGenerator
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenRouterChallengeGenerator @Inject constructor(
    private val aiApi: AiApi,
    private val gson: Gson
) : ChallengeGenerator {

    override suspend fun generateChallenges(city: String, weather: WeatherCondition?): List<Challenge> {
        val weatherDescription = when(weather) {
            WeatherCondition.SUNNY -> "Soleado y despejado"
            WeatherCondition.RAINY -> "Lluvioso"
            WeatherCondition.CLOUDY -> "Nublado"
            WeatherCondition.COLD -> "Frío"
            WeatherCondition.WINDY -> "Ventoso o con tormentas"
            WeatherCondition.HOT -> "Caluroso"
            WeatherCondition.UNKNOWN -> "Normal"
            null -> "No disponible (genera retos genéricos)"
        }

        val prompt = if (weather != null) {
            """
                Actúa como un experto en sostenibilidad y ecología urbana. 
                Contexto: El usuario se encuentra en la ciudad de $city y el clima actual es $weatherDescription.
                
                Tu tarea es generar exactamente 5 retos ecológicos diarios:
                1. Dos (2) retos directamente relacionados con el clima actual ($weatherDescription) en $city.
                2. Tres (3) retos de sostenibilidad general que se puedan realizar independientemente del clima.
            """.trimIndent()
        } else {
            """
                Actúa como un experto en sostenibilidad y ecología urbana. 
                Contexto: No tenemos información precisa del clima o ubicación detallada del usuario.
                
                Tu tarea es generar exactamente 5 retos ecológicos diarios:
                Genera 5 retos de sostenibilidad general y ahorro de recursos que se puedan realizar en cualquier lugar y bajo cualquier clima.
            """.trimIndent()
        } + """
            
            Para cada reto, asigna una puntuación entre 10 y 100 basada en la dificultad percibida.
            
            Responde EXCLUSIVAMENTE en formato JSON con la siguiente estructura:
            {
              "challenges": [
                {
                  "title": "Título corto y motivador",
                  "description": "Descripción clara y accionable",
                  "points": 50,
                  "category": "AGUA" 
                }
              ]
            }
            
            Las categorías válidas son: AGUA, ENERGIA, RESIDUOS, MOVILIDAD, GENERAL.
            El idioma debe ser Español. No incluyas explicaciones, saludos ni bloques de código Markdown, solo el objeto JSON crudo.
        """.trimIndent()

        val apiKey = "Bearer ${BuildConfig.OPEN_ROUTER_API_KEY.trim()}"
        
        if (BuildConfig.OPEN_ROUTER_API_KEY.isEmpty()) {
            Log.e("OpenRouterAI", "ERROR: OPEN_ROUTER_API_KEY está vacía. Revisa tu local.properties")
            return emptyList()
        }

        Log.d("OpenRouterAI", "Solicitando retos a OpenRouter (Auto-Free Model). Clima: $weatherDescription")

        return try {
            val request = OpenRouterRequestDto(
                model = "openrouter/free",
                messages = listOf(
                    OpenRouterMessageDto(role = "user", content = prompt)
                )
            )

            val response = aiApi.generateContent(
                token = apiKey,
                request = request
            )

            val text = response.choices.firstOrNull()?.message?.content
            if (text == null) {
                Log.e("OpenRouterAI", "OpenRouter devolvió una respuesta vacía")
                return emptyList()
            }

            val jsonString = text.replace("```json", "")
                .replace("```", "")
                .trim()
            
            Log.d("OpenRouterAI", "JSON recibido de OpenRouter: $jsonString")
            
            val dto = gson.fromJson(jsonString, GeminiResponseDto::class.java)
            
            dto.challenges.mapIndexed { index, item ->
                Challenge(
                    id = "gen_${System.currentTimeMillis()}_$index", 
                    title = item.title,
                    description = item.description,
                    category = try { HabitCategory.valueOf(item.category.uppercase()) } catch (_: Exception) { HabitCategory.GENERAL },
                    points = item.points,
                    isCompleted = false
                )
            }
        } catch (e: Exception) {
            Log.e("OpenRouterAI", "Error al generar o parsear retos: ${e.message}")
            emptyList()
        }
    }
}
