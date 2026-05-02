package com.ecohabits.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.theme.EcoHabitsTheme
import androidx.compose.ui.graphics.Color
import com.ecohabits.ui.theme.EcoCardLightBackground
import com.ecohabits.ui.theme.EcoCardDarkBackground


@Composable
fun EcoCard(
    modifier: Modifier = Modifier, // Modificador
    onClick: (() -> Unit)? = null, // Acción al hacer clic, opcional
    elevation: androidx.compose.material3.CardElevation = CardDefaults.cardElevation(), // Elevación
    containerColor: Color = if (isSystemInDarkTheme() == true) EcoCardDarkBackground else EcoCardLightBackground, // Color de fondo
    content: @Composable () -> Unit // Contenido del card
) {
    Card(
        onClick = onClick ?: {},
        modifier = modifier,
        elevation = elevation,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun EcoCardPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Eco icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Column {
                    Text(
                        text = "Hábitos Ecológicos",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Aprende a cuidar el planeta con pequeños cambios diarios.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EcoCardDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Eco icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Column {
                    Text(
                        text = "Hábitos Ecológicos",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Aprende a cuidar el planeta con pequeños cambios diarios.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
