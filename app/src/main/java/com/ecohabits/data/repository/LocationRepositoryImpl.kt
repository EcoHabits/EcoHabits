package com.ecohabits.data.repository

import com.ecohabits.data.remote.LocationApi
import com.ecohabits.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi
) : LocationRepository {

    override suspend fun getCityName(lat: Double, lon: Double): String {
        return try {
            val response = locationApi.getCity(lat, lon)
            val addr = response.address
            
            // Priorizamos city, luego town, luego village, luego suburb
            addr.city ?: addr.town ?: addr.village ?: addr.suburb ?: "Ciudad desconocida"
        } catch (e: Exception) {
            "Error al obtener ciudad"
        }
    }
}
