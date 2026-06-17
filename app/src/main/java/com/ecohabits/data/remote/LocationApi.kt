package com.ecohabits.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {
    @GET("reverse")
    suspend fun getCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("format") format: String = "json"
    ) : LocationResponseDto
}