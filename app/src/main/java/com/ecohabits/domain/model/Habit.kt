package com.ecohabits.domain.model

import com.ecohabits.core.model.HabitCategory

data class Habit(
    val id: String,
    val title: String,
    val category: HabitCategory
)

