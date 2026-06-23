package com.ecohabits.domain.usecase

import android.util.Log
import com.ecohabits.data.local.dao.UserDAO
import com.ecohabits.domain.model.UserContext
import com.ecohabits.domain.model.WeatherCondition
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.LocationRepository
import com.ecohabits.domain.repository.WeatherRepository
import com.ecohabits.services.LocationTracker
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class GetCurrentContextUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserContext? {
        Log.d("UserContext", "Iniciando obtención de contexto...")

        // 1. Obtenemos el nombre del usuario (local de la sesión)
        val userName = authRepository.getCurrentUserName()
        
        // 2. Esperamos ubicación con un tiempo límite de 10 segundos
        Log.d("UserContext", "Esperando ubicación de LocationTracker...")
        val location = withTimeoutOrNull(10000) {
            LocationTracker.locationData.filterNotNull().first()
        }

        if (location == null) {
            Log.w("UserContext", "GPS no detectado. Generando contexto sin clima.")
            return UserContext(
                cityName = "Ubicación desconocida",
                userName = userName,
                weatherCondition = null
            )
        }
        
        val lat = location.latitude
        val lon = location.longitude
        Log.d("UserContext", "Ubicación obtenida: $lat, $lon")

        val cityName = try {
            locationRepository.getCityName(lat, lon)
        } catch (e: Exception) {
            Log.e("UserContext", "Error al obtener ciudad: ${e.message}")
            "Ciudad desconocida"
        }

        val weather = try {
            weatherRepository.getWeatherState(lat, lon)
        } catch (e: Exception) {
            Log.e("UserContext", "Error al obtener clima: ${e.message}")
            null
        }

        Log.d("UserContext", "Contexto final -> Ciudad: $cityName, Usuario: $userName, Clima: $weather")
        
        return UserContext(
            cityName = cityName,
            userName = userName,
            weatherCondition = weather
        )
    }
}
