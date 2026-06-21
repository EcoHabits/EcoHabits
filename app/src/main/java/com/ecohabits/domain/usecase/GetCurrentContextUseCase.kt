package com.ecohabits.domain.usecase

import com.ecohabits.domain.model.UserContext
import com.ecohabits.domain.model.WeatherCondition
import com.ecohabits.domain.repository.LocationRepository
import com.ecohabits.domain.repository.WeatherRepository
import com.ecohabits.services.LocationTracker
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCurrentContextUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): UserContext? {
        // Esperamos a que el LocationTracker tenga la ubicación del OneShot
        val location = LocationTracker.locationData.filterNotNull().first()
        
        val lat = location.latitude
        val lon = location.longitude

        return try {
            //Llamamos a ambas APIs en paralelo o secuencial (secuencial aquí por simplicidad)
            val cityName = locationRepository.getCityName(lat, lon)
            val weather = weatherRepository.getWeatherState(lat, lon)

            UserContext(
                cityName = cityName,
                weatherCondition = weather
            )
        } catch (e: Exception) {
            null
        }
    }
}
