package com.ecohabits.data.repository

import com.ecohabits.domain.model.WeatherAdvice
import com.ecohabits.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    override suspend fun getWeatherAdvice(): List<WeatherAdvice> = TODO("Skeleton only")
}

