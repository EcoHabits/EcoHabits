package com.ecohabits.services

import android.location.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object LocationTracker {
    // Usamos MutableStateFlow para mantener el último estado de la ubicación
    private val _locationData = MutableStateFlow<Location?>(null)
    val locationData = _locationData.asStateFlow()

    fun updateLocation(location: Location) {
        _locationData.value = location
    }
}