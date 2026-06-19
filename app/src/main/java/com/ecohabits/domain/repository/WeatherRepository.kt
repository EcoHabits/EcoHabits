package com.ecohabits.domain.repository

import com.ecohabits.domain.model.WeatherCondition

interface WeatherRepository {
    suspend fun getWeatherState(lat: Double, lon: Double): WeatherCondition
}

