package com.ecohabits.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ecohabits.core.model.HabitCategory

@Entity(tableName = "TblChallenges")
data class WeatherChallengeEntity (
    @PrimaryKey
    val id: String,
    /*val weatherCondition: WeatherCondition,
    val category: HabitCategory,
    val titleEs: String,
    val messageEs: String*/
    val ChallengeName: String,
    val ChallengeDescription: String,
)