package com.ecohabits.domain.repository

import com.ecohabits.domain.model.WeatherState

interface WeatherRepository {
    suspend fun getWeatherState(): WeatherState
}

