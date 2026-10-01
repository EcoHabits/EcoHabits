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

    @Query(
        """
        UPDATE TblChallenges
        SET isCompleted = :isCompleted
        WHERE id = :challengeId
        """
    )
    suspend fun updateChallengeStatus(
        challengeId: String,
        isCompleted: Boolean
    )

    @Query("DELETE FROM TblChallenges")
    suspend fun deleteAllChallenges()
}
