package com.ecohabits.presentation.challenges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.usecase.GetDailyChallengesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengesViewModel @Inject constructor(
    private val getDailyChallengesUseCase: GetDailyChallengesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChallengesUiState())
    val uiState: StateFlow<ChallengesUiState> = _uiState.asStateFlow()

    init {
        loadChallenges()
    }

    fun loadChallenges() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            getDailyChallengesUseCase()
                .onSuccess { challenges ->
                    _uiState.update { it.copy(challenges = challenges, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun toggleChallenge(challenge: Challenge, isCompleted: Boolean) {
        viewModelScope.launch {
            // Actualización optimista en la UI
            _uiState.update { state ->
                state.copy(
                    challenges = state.challenges.map {
                        if (it.id == challenge.id) it.copy(isCompleted = isCompleted) else it
                    }
                )
            }
            
            // Aquí iría la llamada al repositorio para actualizar Supabase/Room
            // challengeRepository.updateChallengeStatus(...)
        }
    }
}

data class ChallengesUiState(
    val challenges: List<Challenge> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
