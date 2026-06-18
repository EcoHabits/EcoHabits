package com.ecohabits.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ecohabits.services.LocationForegroundService
import android.content.Intent
import android.os.Build
import androidx.compose.ui.platform.LocalContext

@Composable
fun LocationPermissionHandler(
    onPermissionsDenied: () -> Unit = {}
) {
    val context = LocalContext.current

    // Este es el "Lanzador" en versión Compose
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineLocationGranted || coarseLocationGranted) {
            // PERMISO CONCEDIDO: Iniciamos el servicio
            val intent = Intent(context, LocationForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        } else {
            // PERMISO DENEGADO
            onPermissionsDenied()
        }
    }

    // Se ejecuta una sola vez cuando el componente entra en la pantalla
    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}