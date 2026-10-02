package com.ecohabits.presentation.progress

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProgressRoute(
    viewModel: ProgressViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    ProgressScreen(
        uiState = uiState,
        onDismissBadgeUnlock = {
            viewModel.dismissBadgeUnlock()
        }
    )
}