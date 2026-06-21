package com.ecohabits.data.remote

import com.ecohabits.data.remote.dto.OpenRouterRequestDto
import com.ecohabits.data.remote.dto.OpenRouterResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AiApi {
    @POST("api/v1/chat/completions")
    suspend fun generateContent(
        @Header("Authorization") token: String,
        @Body request: OpenRouterRequestDto
    ): OpenRouterResponseDto
}
