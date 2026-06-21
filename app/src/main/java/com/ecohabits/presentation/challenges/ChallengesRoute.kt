package com.ecohabits.presentation.challenges

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ChallengesRoute(
    viewModel: ChallengesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ChallengesScreen(
        uiState = uiState,
        onChallengeCheckedChange = { challenge, isChecked ->
            viewModel.toggleChallenge(challenge, isChecked)
        },
        onRetry = {
            viewModel.loadChallenges()
        }
    )
}

