package com.ecohabits.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecohabits.data.local.WeatherChallengeEntity
import com.ecohabits.data.local.dao.WeatherChallengeDAO

@Database(entities = [WeatherChallengeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherChallengeDAO() : WeatherChallengeDAO
}