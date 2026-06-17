package com.ecohabits.services
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi



class LocationForegroundService : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    fun requestPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
            { permissions ->
                when{
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        // Permiso para la location precisa
                    }
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Permiso para la location aproximada
                    }
                    else -> {
                        // No hay permiso
                    }
                }
            }
        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}