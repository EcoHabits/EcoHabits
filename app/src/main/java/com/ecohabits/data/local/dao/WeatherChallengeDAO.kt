package com.ecohabits.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ecohabits.domain.model.Challenge

@Dao
interface WeatherChallengeDAO {
    @Query ("SELECT * FROM TblChallenges")
    fun loadAllChallenges(): LiveData<List<Challenge>>
    // Live Data nos permite que el estado de los datos coincida con la ui, ademas de evitar fugas de memoria
}