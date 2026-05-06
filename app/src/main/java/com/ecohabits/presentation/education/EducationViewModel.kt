package com.ecohabits.presentation.education

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class EducationViewModel : ViewModel() {

    private val mockArticles = listOf(EducationArticle(

        title = "Como ahorrar agua en casa",
        summary = "Pequeños cambios diarios que pueden reducir el consumo de agua y ayudar al planeta."

    ),
        EducationArticle(
            title = "Uso responsable de energia",
            summary = "Apagar las luces, desconectar cargadores y utilizar fuentes de luz natural como habitos importantes"

        ),

        EducationArticle(
            title = "Separacion correcta de residuos",
            summary = "Clasificar residuos facilita el reciclaje y disminuye contaminacion ambiental. "

        )
    )

    private val _uiState = MutableStateFlow(
        EducationUiState(
            articles = mockArticles
        )
    )

    val uiState: StateFlow<EducationUiState> = _uiState

    fun  onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                articles = mockArticles.filter { article ->
                    article.title.contains(query, ignoreCase = true) ||
                            article.summary.contains(query, ignoreCase = true) })
        }

    }

}