package com.ecohabits.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BadgeDto(

    @SerialName("IdBadge")
    val idBadge: String,

    @SerialName("BadgeName")
    val badgeName: String,

    @SerialName("BadgeDescription")
    val badgeDescription: String,

    @SerialName("BadgeCode")
    val badgeCode: String? = null,

    @SerialName("RuleType")
    val ruleType: String? = null,

    @SerialName("RuleValue")
    val ruleValue: Int? = null
)