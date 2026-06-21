package com.ecohabits.domain.repository

interface LocationRepository {
    suspend fun getCityName(lat: Double, lon: Double): String
}
