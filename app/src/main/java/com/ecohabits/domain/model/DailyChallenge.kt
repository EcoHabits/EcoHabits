package com.ecohabits.domain.model

data class DailyChallenge(
    val id: String,
    val habit: Habit,
    val completed: Boolean
)

