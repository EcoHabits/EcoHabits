package com.ecohabits.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.theme.EcoHabitsTheme

@Composable
fun EcoListItem(
    icon: ImageVector? = null, // Ícono del ítem
    title: String, // Título
    subtitle: String? = null, // Subtítulo opcional
    trailing: @Composable (() -> Unit)? = null, // Elemento trailing opcional
    modifier: Modifier = Modifier, // Modificador
    onClick: (() -> Unit)? = null // Acción al hacer clic, opcional
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            subtitle?.let {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        trailing?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun EcoListItemTitleOnlyPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoListItem(
            icon = Icons.Default.AccessTime,
            title = "Reciclar papel"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoListItemWithSubtitlePreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoListItem(
            icon = Icons.Default.AccessTime,
            title = "Reciclar papel",
            subtitle = "Ahorra recursos naturales"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoListItemWithTrailingPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoListItem(
            icon = Icons.Default.AccessTime,
            title = "Reciclar papel",
            trailing = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completado",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoListItemTitleOnlyDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoListItem(
            icon = Icons.Default.AccessTime,
            title = "Reciclar papel"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoListItemWithSubtitleDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoListItem(
            icon = Icons.Default.AccessTime,
            title = "Reciclar papel",
            subtitle = "Ahorra recursos naturales"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoListItemWithTrailingDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoListItem(
            icon = Icons.Default.AccessTime,
            title = "Reciclar papel",
            trailing = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completado",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}
