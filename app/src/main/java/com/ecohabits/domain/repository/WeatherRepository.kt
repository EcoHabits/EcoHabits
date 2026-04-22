package com.ecohabits.domain.repository

import com.ecohabits.domain.model.WeatherAdvice

interface WeatherRepository {
    suspend fun getWeatherAdvice(): List<WeatherAdvice>
}

