package com.ecohabits.data.remote.supabase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("IdUser") val idUser: String, // El UUID de auth.users
    @SerialName("FirstName") val firstName: String,
    @SerialName("LastName") val lastName: String,
    @SerialName("UserName") val userName: String,
    @SerialName("CurrentStreak") val currentStreak: String = "0",
    @SerialName("MaxiumStreak") val maximumStreak: String = "0"
)