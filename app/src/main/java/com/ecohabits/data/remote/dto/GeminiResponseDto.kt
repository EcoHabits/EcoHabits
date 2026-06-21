package com.ecohabits.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GeminiResponseDto(
    @SerializedName("challenges")
    val challenges: List<GeminiChallengeDto>
)

data class GeminiChallengeDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("points")
    val points: Int,
    @SerializedName("category")
    val category: String // Ej: "AGUA", "ENERGIA"
)
