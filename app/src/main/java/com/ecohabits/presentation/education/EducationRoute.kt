package com.ecohabits.presentation.education

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EducationRoute(viewModel: EducationViewModel = viewModel()){

    val uiState by viewModel.uiState.collectAsState()

    EducationScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange
    )
}