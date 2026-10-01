package com.ecohabits.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ecohabits.domain.model.HabitCategory

@Entity(tableName = "TblChallenges")
data class ChallengeEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val category: HabitCategory,
    val points: Int,
    val isCompleted: Boolean = false,
    val co2Savings: Double = 0.0,
    val waterSavings: Double = 0.0
)