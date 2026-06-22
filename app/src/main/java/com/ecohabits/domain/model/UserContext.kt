package com.ecohabits.domain.model

data class UserContext(
    val cityName: String,
    val userName: String? = null,
    val weatherCondition: WeatherCondition? = null
)
