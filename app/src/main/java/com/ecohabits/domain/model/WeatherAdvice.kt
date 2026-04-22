package com.ecohabits.domain.model

import com.ecohabits.core.model.HabitCategory

data class WeatherAdvice(
    val category: HabitCategory,
    val title: String,
    val message: String
)

