package com.ecohabits.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ecohabits.domain.model.WeatherChallenge

@Dao
interface WeatherChallengeDAO {
    @Query ("SELECT * FROM TblChallenges")
    fun LoadAllChallenges(): Array<WeatherChallenge>
}