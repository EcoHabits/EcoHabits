package com.ecohabits.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.theme.EcoHabitsTheme

@Composable
fun EcoButton(
    onClick: () -> Unit, // Acción al hacer clic
    text: String, // Texto del botón
    modifier: Modifier = Modifier, // Modificador para personalizar el botón
    enabled: Boolean = true, // Si el botón está habilitado
    loading: Boolean = false // Si muestra indicador de carga
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EcoButtonEnabledPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoButton(
            onClick = {},
            text = "Guardar",
            enabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoButtonDisabledPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoButton(
            onClick = {},
            text = "Guardar",
            enabled = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoButtonLoadingPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoButton(
            onClick = {},
            text = "Guardar",
            loading = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoButtonEnabledDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoButton(
            onClick = {},
            text = "Guardar",
            enabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun EcoButtonDisabledDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoButton(
            onClick = {},
            text = "Guardar",
            enabled = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun EcoButtonLoadingDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoButton(
            onClick = {},
            text = "Guardar",
            loading = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}
