package com.ecohabits.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TblChallenges")
data class WeatherChallengeEntity (
    @PrimaryKey
    val id : String,
    val ChallengeName : String,
    val ChallengeDescription : String

)