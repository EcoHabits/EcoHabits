package com.ecohabits.services
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*

class LocationForegroundService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate() {
        super.onCreate()

        // 1. Inicializamos el cliente de ubicación de Google
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // 2. Definimos qué hacer cuando recibamos una nueva ubicación
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    // AQUÍ TIENES LA UBICACIÓN REAL
                    val latitude = location.latitude
                    val longitude = location.longitude

                    Log.d("LocationService", "Nueva ubicación: Lat $latitude, Lon $longitude")

                    // TODO: Aquí puedes enviar los datos a tu base de datos o ViewModel
                    LocationTracker.updateLocation(location)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 3. Iniciamos el servicio en modo Foreground (con notificación)
        startForeground(1, createNotification())

        // 4. Configuramos la frecuencia con la que queremos recibir la ubicación
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, // Máxima precisión (usa GPS)
            10000 // Intervalo de 10 segundos
        ).setMinUpdateIntervalMillis(5000) // No actualizar más rápido de cada 5 seg
            .build()

        // 5. Solicitamos las actualizaciones
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        // START_STICKY hace que si el sistema mata el servicio, intente recrearlo
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // 6. MUY IMPORTANTE: Detenemos el GPS cuando el servicio se cierra
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("LocationService", "Servicio detenido y GPS apagado")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // Función para crear la notificación obligatoria
    private fun createNotification(): Notification {
        val channelId = "location_updates"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Seguimiento de Ubicación",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("EcoHabits en ejecución")
            .setContentText("Estamos optimizando tu experiencia con tu ubicación actual")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true) // Evita que el usuario la quite deslizando
            .build()
    }
}