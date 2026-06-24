package com.ecohabits.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecohabits.data.local.entity.ChallengeEntity

@Dao
interface ChallengeDao {
    @Upsert
    suspend fun upsertChallenges(challenges: List<ChallengeEntity>)

    @Query("SELECT * FROM TblChallenges")
    suspend fun getAllChallenges(): List<ChallengeEntity>

    @Query("DELETE FROM TblChallenges")
    suspend fun deleteAllChallenges()
}
