package com.ecohabits.presentation.education

data class EducationUiState(
    val searchQuery: String = "",
    val articles: List<EducationArticle> = emptyList())

data class EducationArticle(
    val title: String,
    val summary: String
)
