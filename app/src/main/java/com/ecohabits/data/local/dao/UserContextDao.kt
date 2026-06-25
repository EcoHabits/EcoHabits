package com.ecohabits.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecohabits.data.local.entity.UserContextEntity
import com.ecohabits.domain.model.WeatherCondition
import kotlinx.coroutines.flow.Flow

@Dao
interface UserContextDao {
    @Upsert
    suspend fun upsertUser(userContextEntity: UserContextEntity)

    @Query("SELECT * FROM TblUser")
    fun getUser(): Flow<UserContextEntity?>

    @Query("SELECT idUser FROM TblUser")
    fun LoadIdUser(): Int?

    @Query("SELECT userName from TblUser")
    fun LoadUserName(): String?

    @Query("SELECT weatherCondition from TblUser")
    fun LoadWeatherCondition(): WeatherCondition?

    @Query("SELECT cityName from TblUser")
    fun LoadCityName(): String?

    @Query("DELETE FROM TblUser")
    suspend fun deleteUser()

    @Query("UPDATE TblUser SET weatherCondition = :weatherCondition")
    suspend fun updateWeatherCondition(weatherCondition: WeatherCondition)
}