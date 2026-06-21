package com.ecohabits.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun LocationRationaleDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(Icons.Filled.LocationOn, contentDescription = null)
        },
        title = {
            Text(text = "Mejora tu experiencia ecológica")
        },
        text = {
            Text(
                text = "EcoHabits necesita acceder a tu ubicación para generar retos personalizados basados en el clima actual de tu ciudad. Esto nos permite sugerirte acciones relevantes para cuidar el planeta en tiempo real."
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Entendido")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Ahora no")
            }
        }
    )
}
