package com.ecohabits.data.remote

import com.ecohabits.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeatherState(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "weather_code",
        @Query("past_days") pastDays: Int = 0,
        @Query("forecast_days") forecastDays: Int = 0
    ): WeatherResponseDto
}