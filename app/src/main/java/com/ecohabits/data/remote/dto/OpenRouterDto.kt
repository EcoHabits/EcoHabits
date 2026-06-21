package com.ecohabits.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenRouterRequestDto(
    @SerializedName("model")
    val model: String,
    @SerializedName("messages")
    val messages: List<OpenRouterMessageDto>
)

data class OpenRouterMessageDto(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

data class OpenRouterResponseDto(
    @SerializedName("choices")
    val choices: List<OpenRouterChoiceDto>
)

data class OpenRouterChoiceDto(
    @SerializedName("message")
    val message: OpenRouterMessageDto
)
