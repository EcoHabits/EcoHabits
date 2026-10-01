package com.ecohabits.domain.model

data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val category: HabitCategory,
    val points: Int,
    val isCompleted: Boolean = false,
    val co2Savings: Double = 0.0,
    val waterSavings: Double = 0.0
)
