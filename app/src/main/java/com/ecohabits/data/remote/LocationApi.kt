package com.ecohabits.data.remote

import com.ecohabits.data.remote.dto.LocationResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LocationApi {
    @Headers("User-Agent: EcoHabits-Android")
    @GET("reverse")
    suspend fun getCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("format") format: String = "json"
    ) : LocationResponseDto
}