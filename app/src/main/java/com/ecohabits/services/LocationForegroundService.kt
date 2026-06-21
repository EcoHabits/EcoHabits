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

        // se inicializa el cliente de ubicación de Google
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // lo que se hace cuando se recibe una nueva ubicacioin
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    Log.d("LocationService", "Ubicación obtenida: Lat ${location.latitude}, Lon ${location.longitude}")
                    
                    // Actualizamos el tracker con la ubicación real
                    LocationTracker.updateLocation(location)
                    
                    // ONESHOT: Una vez obtenida la ubicación, detenemos las actualizaciones y el servicio
                    fusedLocationClient.removeLocationUpdates(this)
                    stopSelf()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // servicio en modo Foreground (con notificación solo cuando se esta ejecutando la app)
        startForeground(1, createNotification())

        // Intentamos obtener la última ubicación conocida inmediatamente para agilizar
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                Log.d("LocationService", "Última ubicación conocida: Lat ${location.latitude}, Lon ${location.longitude}")
                LocationTracker.updateLocation(location)
            }
        }

        // 4. Configuramos la solicitud de ubicación
        // Usamos un intervalo corto inicialmente para obtener la primera ubicación rápido
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000 
        ).setMaxUpdates(1) // Solo queremos una actualización
            .build()

        // 5. Solicitamos la actualización
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        // START_NOT_STICKY: No hace falta recrearlo si se mata, ya que se lanza al abrir la app
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
      //Detenemos el GPS cuando el servicio se cierra
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