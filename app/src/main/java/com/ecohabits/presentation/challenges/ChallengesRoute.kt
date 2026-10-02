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
        challenges = uiState.challenges,
        onChallengeClick = { challenge ->

            viewModel.toggleChallenge(
                challenge = challenge,
                isCompleted = !challenge.isCompleted
            )
        }
    )
}