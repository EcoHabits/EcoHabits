package com.ecohabits.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ecohabits.presentation.home.EducationTipUiState
import com.ecohabits.ui.theme.EcoHabitsTypography

@Composable
fun EducationTipCard(tip: EducationTipUiState, onReadMoreClick: () -> Unit){

    EcoCard(modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {
            Text( text = "Dato educativo del día",
                style = EcoHabitsTypography().titleMedium,
                fontWeight = FontWeight.Bold)

            Text(text = tip.summary ,
                style = EcoHabitsTypography().bodyMedium)

            EcoButton(text = "Leer mas",
                onClick = onReadMoreClick)
        }
    }

}