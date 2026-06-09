package com.ecohabits.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.ecohabits.ui.theme.EcoHabitsTheme

data class NavigationItem(
    val icon: ImageVector, // Ícono del ítem
    val label: String, // Etiqueta
    val route: String // Ruta
)

@Composable
fun BottomNavigationBar(
    selectedItem: Int, // Índice del ítem seleccionado
    onItemSelected: (Int) -> Unit, // Función al seleccionar ítem
    items: List<NavigationItem>, // Lista de ítems
    modifier: Modifier = Modifier // Modificador
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    EcoHabitsTheme(darkTheme = false) {
        val items = listOf(
            NavigationItem(Icons.Default.DateRange, "Hoy", "home"),
            NavigationItem(Icons.Default.List, "Hábitos", "habits"),
            NavigationItem(Icons.Default.Person, "Perfil", "profile")
        )
        BottomNavigationBar(
            selectedItem = 1,
            onItemSelected = {},
            items = items
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        val items = listOf(
            NavigationItem(Icons.Default.DateRange, "Hoy", "home"),
            NavigationItem(Icons.Default.List, "Hábitos", "habits"),
            NavigationItem(Icons.Default.Person, "Perfil", "profile")
        )
        BottomNavigationBar(
            selectedItem = 1,
            onItemSelected = {},
            items = items
        )
    }
}
