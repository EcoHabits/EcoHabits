package com.ecohabits.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ecohabits.domain.model.WeatherCondition

@Entity (tableName = "TblUser")
data class UserContextEntity(
    @PrimaryKey(autoGenerate = true)
    val idUser: Int,
    val cityName: String,
    val userName: String? = null,
    val weatherCondition: WeatherCondition? = null
)
