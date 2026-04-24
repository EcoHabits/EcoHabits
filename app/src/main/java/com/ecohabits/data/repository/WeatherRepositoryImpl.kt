package com.ecohabits.data.repository

import com.ecohabits.data.remote.WeatherApi
import com.ecohabits.domain.model.WeatherState
import com.ecohabits.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val WeatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherState(): WeatherState = TODO("Skeleton only")
}


