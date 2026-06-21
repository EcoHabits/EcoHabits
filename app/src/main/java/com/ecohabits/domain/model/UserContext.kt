package com.ecohabits.domain.model

data class UserContext(
    val cityName: String,
    val weatherCondition: WeatherCondition? = null
)
