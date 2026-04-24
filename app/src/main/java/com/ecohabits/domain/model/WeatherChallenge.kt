package com.ecohabits.domain.model

import com.ecohabits.core.model.HabitCategory
import com.ecohabits.data.local.WeatherCondition

data class WeatherChallenge(
    val id: String,
    val weatherCondition: WeatherCondition,
    val category: HabitCategory,
    val titleEs: String,
    val messageEs: String
)

