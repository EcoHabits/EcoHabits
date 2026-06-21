package com.ecohabits.data.repository

import com.ecohabits.data.remote.WeatherApi
import com.ecohabits.data.remote.dto.WeatherResponseDto
import com.ecohabits.domain.model.WeatherCondition
import com.ecohabits.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherState(lat: Double, lon: Double): WeatherCondition {
        val response: WeatherResponseDto = weatherApi.getWeatherState(lat, lon)
        return mapCodeToCondition(response.current.weatherCode)
    }

    private fun mapCodeToCondition(code: Int): WeatherCondition {
        return when (code) {
            0 -> WeatherCondition.SUNNY
            in 1..3 -> WeatherCondition.CLOUDY
            in 45..48 -> WeatherCondition.CLOUDY // Niebla
            in 51..55, in 61..67, in 80..82 -> WeatherCondition.RAINY // Llovizna, Lluvia y Chubascos
            in 71..77, in 85..86 -> WeatherCondition.COLD // Nieve y granizo
            in 95..99 -> WeatherCondition.WINDY // Tormentas
            // Nota: Para condiciones de calor extremo (HOT), Open-Meteo no tiene un código específico, 
            // se suele inferir por temperatura, pero para este flujo lo mapearemos a SUNNY o UNKNOWN 
            // a menos que se quiera añadir lógica de temperatura luego.
            else -> WeatherCondition.UNKNOWN
        }
    }
}