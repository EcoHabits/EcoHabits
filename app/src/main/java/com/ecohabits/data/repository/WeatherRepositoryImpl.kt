package com.ecohabits.data.repository

import com.ecohabits.data.remote.WeatherApi
import com.ecohabits.data.remote.WeatherResponseDto
import com.ecohabits.domain.model.WeatherState
import com.ecohabits.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherState(lat: Double, lon: Double): WeatherState {
        val response: WeatherResponseDto = weatherApi.getWeatherState(lat, lon)
        return WeatherState(weatherCode = response.current.weatherCode)
    }
}