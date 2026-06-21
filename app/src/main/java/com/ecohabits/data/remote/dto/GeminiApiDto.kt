package com.ecohabits.data.remote.dto

import com.google.gson.annotations.SerializedName

// Request DTOs
data class GeminiRequestDto(
    @SerializedName("contents")
    val contents: List<GeminiContentDto>
)

data class GeminiContentDto(
    @SerializedName("parts")
    val parts: List<GeminiPartDto>
)

data class GeminiPartDto(
    @SerializedName("text")
    val text: String
)

// Response DTOs (Raw from API)
data class GeminiResponseRawDto(
    @SerializedName("candidates")
    val candidates: List<GeminiCandidateDto>
)

data class GeminiCandidateDto(
    @SerializedName("content")
    val content: GeminiContentDto
)
