package com.ecohabits.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBadgeDto(
    @SerialName("IdBadgeFK")
    val idBadgeFK: String,

    @SerialName("IdUserFK")
    val idUserFK: String,

    @SerialName("Date")
    val date: String
)