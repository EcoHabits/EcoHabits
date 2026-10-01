package com.ecohabits.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserChallengeDto(

    @SerialName("IdUserFK")
    val idUserFK: String,

    @SerialName("IdChallengeFK")
    val idChallengeFK: String,

    @SerialName("DateObtained")
    val dateObtained: String,

    @SerialName("IsCompleted")
    val isCompleted: Boolean,

    @SerialName("CompletedAt")
    val completedAt: String? = null
)