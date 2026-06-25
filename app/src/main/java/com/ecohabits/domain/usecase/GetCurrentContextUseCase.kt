package com.ecohabits.domain.usecase

import android.util.Log
import com.ecohabits.domain.model.UserContext
import com.ecohabits.domain.model.WeatherCondition
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

        return try {
            // 1. Intentamos obtener datos "frescos"
            val userName = authRepository.getCurrentUserName()
            
            // Timeout de 10 segundos para la ubicación
            val location = withTimeoutOrNull(10000) {
                LocationTracker.locationData.filterNotNull().first()
            } ?: throw Exception("Ubicación no detectada")

            val lat = location.latitude
            val lon = location.longitude
            Log.d("UserContext", "Ubicación obtenida: $lat, $lon")

            val cityName = locationRepository.getCityName(lat, lon)
            val weather = weatherRepository.getWeatherState(lat, lon)

            val finalContext = UserContext(
                cityName = cityName,
                userName = userName,
                weatherCondition = weather
            )

            // Si todo salió bien, guardamos en Room para el futuro
            Log.d("UserContext", "Contexto obtenido con éxito. Guardando en Room.")
            userContextRepository.saveUser(finalContext)
            
            finalContext

        } catch (e: Exception) {
            Log.w("UserContext", "Error obteniendo datos en vivo: ${e.message}. Cargando fallback de Room.")
            
            // 3. FALLBACK: Intentamos recuperar lo último que guardamos en Room
            val cachedContext = userContextRepository.getUser().first()
            
            // Si incluso Room está vacío, devolvemos un objeto por defecto
            cachedContext ?: UserContext(
                cityName = "Ubicación desconocida",
                userName = authRepository.getCurrentUserName(),
                weatherCondition = WeatherCondition.UNKNOWN
            )
        }
    }
}
