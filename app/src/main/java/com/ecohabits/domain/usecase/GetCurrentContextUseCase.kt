package com.ecohabits.domain.usecase

import android.util.Log
import com.ecohabits.domain.model.UserContext
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.LocationRepository
import com.ecohabits.domain.repository.UserContextRepository
import com.ecohabits.domain.repository.WeatherRepository
import com.ecohabits.services.LocationTracker
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class GetCurrentContextUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
    private val authRepository: AuthRepository,
    private val userContextRepository: UserContextRepository
) {
    suspend operator fun invoke(): UserContext? {
        Log.d("UserContext", "Iniciando obtención de contexto...")

        try {
            // 1. Obtenemos el nombre del usuario (local de la sesión)
            val userName = authRepository.getCurrentUserName()
            
            // 2. Esperamos ubicación con un tiempo límite de 10 segundos
            Log.d("UserContext", "Esperando ubicación de LocationTracker...")
            val location = withTimeoutOrNull(10000) {
                LocationTracker.locationData.filterNotNull().first()
            }

            if (location == null) {
                Log.w("UserContext", "GPS no detectado. Intentando cargar contexto desde Room.")
                val cachedContext = userContextRepository.getUser().first()
                return cachedContext ?: UserContext(
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

            val finalContext = UserContext(
                cityName = cityName,
                userName = userName,
                weatherCondition = weather
            )

            Log.d("UserContext", "Contexto obtenido con éxito. Guardando en Room.")
            userContextRepository.saveUser(finalContext)
            
            return finalContext

        } catch (e: Exception) {
            Log.e("UserContext", "Error crítico obteniendo contexto: ${e.message}. Cargando fallback de Room.")
            return userContextRepository.getUser().first()
        }
    }
}
