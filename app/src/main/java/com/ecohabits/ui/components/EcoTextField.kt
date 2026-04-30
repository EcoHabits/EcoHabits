package com.ecohabits.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ecohabits.ui.theme.EcoHabitsTheme

@Composable
fun EcoTextField(
    value: String, // Valor actual del campo
    onValueChange: (String) -> Unit, // Función para cambiar el valor
    label: String, // Etiqueta del campo
    modifier: Modifier = Modifier, // Modificador
    placeholder: String? = null, // Placeholder opcional
    isError: Boolean = false, // Si hay error
    errorMessage: String? = null, // Mensaje de error opcional
    singleLine: Boolean = true // Si es de una sola línea
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = placeholder?.let { { Text(it) } },
        isError = isError,
        supportingText = errorMessage?.let { { Text(it) } },
        singleLine = singleLine,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun EcoTextFieldEmptyPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoTextField(
            value = "",
            onValueChange = {},
            label = "Nombre",
            placeholder = "Ingresa tu nombre"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoTextFieldWithTextPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoTextField(
            value = "Juan Pérez",
            onValueChange = {},
            label = "Nombre"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoTextFieldErrorPreview() {
    EcoHabitsTheme(darkTheme = false) {
        EcoTextField(
            value = "juan",
            onValueChange = {},
            label = "Email",
            isError = true,
            errorMessage = "Email inválido"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoTextFieldEmptyDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoTextField(
            value = "",
            onValueChange = {},
            label = "Nombre",
            placeholder = "Ingresa tu nombre"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoTextFieldWithTextDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoTextField(
            value = "Juan Pérez",
            onValueChange = {},
            label = "Nombre"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EcoTextFieldErrorDarkPreview() {
    EcoHabitsTheme(darkTheme = true) {
        EcoTextField(
            value = "juan",
            onValueChange = {},
            label = "Email",
            isError = true,
            errorMessage = "Email inválido"
        )
    }
}
