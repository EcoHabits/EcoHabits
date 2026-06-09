package com.ecohabits.domain.repository

import com.ecohabits.domain.model.WeatherState

interface WeatherRepository {
    suspend fun getWeatherState(lat: Double, lon: Double): WeatherState
}

