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
            in 45..48 -> WeatherCondition.CLOUDY // Fog
            in 51..67 -> WeatherCondition.RAINY // Drizzle / Rain
            in 71..77 -> WeatherCondition.COLD // Snow
            in 80..82 -> WeatherCondition.RAINY // Rain showers
            in 95..99 -> WeatherCondition.WINDY // Thunderstorm
            else -> WeatherCondition.UNKNOWN
        }
    }
}