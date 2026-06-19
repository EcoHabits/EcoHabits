package com.ecohabits.domain.model

data class Habit(
    val id: String,
    val title: String,
    val category: HabitCategory
)

