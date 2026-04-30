package com.ecohabits.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    val current: Current
)

data class Current(
    @SerializedName("weather_code")
    val weatherCode: Int
)