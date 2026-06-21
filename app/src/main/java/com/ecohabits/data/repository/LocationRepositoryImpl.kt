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
            // Tomamos la ciudad del DTO. Si viene nulo, devolvemos un valor por defecto.
            response.address.city ?: "Ciudad desconocida"
        } catch (e: Exception) {
            "Error al obtener ciudad"
        }
    }
}
