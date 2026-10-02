package com.ecohabits.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDto(
    @SerialName("IdChallenge")
    val idChallenge: String? = null,
    @SerialName("ChallengeName")
    val challengeName: String,
    @SerialName("ChallengeDescription")
    val challengeDescription: String,
    @SerialName("ChallengeCategory")
    val challengeCategory: String,
    @SerialName("CityName")
    val cityName: String,
    @SerialName("WeatherCondition")
    val weatherCondition: String,
    @SerialName("ChallengePoints")
    val challengePoints: Int,
    @SerialName("CO2Savings")
    val co2Savings: Double? = 0.0,
    @SerialName("WaterSavings")
    val waterSavings: Double? = 0.0
)
