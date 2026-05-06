package com.ecohabits.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    // Lista de tips
    private val educationTips = listOf(
        EducationTipUiState(
            title = "Ahorro de Agua",
            summary = "Cerrar la llave mientras te cepillas puede ahorrar litros de agua diariamente."
        ),
        EducationTipUiState(
            title = "Uso responsable de energía",
            summary = "Apagar luces, desconectar cargadores y usar luz natural son hábitos importantes."
        ),
        EducationTipUiState(
            title = "Separación de residuos",
            summary = "Clasificar residuos facilita el reciclaje y reduce la contaminación."
        ),
        EducationTipUiState(
            title = "Movilidad sostenible",
            summary = "Caminar o usar bicicleta reduce emisiones de CO₂."
        )
    )

    // Función que selecciona el tip del día
    private fun getDailyEducationTip(): EducationTipUiState {
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        return educationTips[dayOfYear % educationTips.size]
    }
    // Estado UI
    private val _uiState = MutableStateFlow(
        HomeUiState(
            educationTip = getDailyEducationTip()
        )
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
}