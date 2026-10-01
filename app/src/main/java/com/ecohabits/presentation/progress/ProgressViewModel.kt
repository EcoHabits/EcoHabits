package com.ecohabits.presentation.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.usecase.GetUserProgressUseCase
import com.ecohabits.domain.usecase.GetUserStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProgressViewModel @Inject constructor(

    private val getUserProgressUseCase: GetUserProgressUseCase,

    private val getUserStreakUseCase: GetUserStreakUseCase

) : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            ProgressUiState()
        )


    val uiState: StateFlow<ProgressUiState> =
        _uiState.asStateFlow()


    init {

        loadProgress()
    }


    fun loadProgress() {

        if (_uiState.value.isLoading) {
            return
        }


        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true,
                    error = null
                )
            }


            val progressResult =
                getUserProgressUseCase()


            val streakResult =
                getUserStreakUseCase()


            if (progressResult.isFailure) {

                _uiState.update {

                    it.copy(
                        isLoading = false,
                        error =
                            progressResult
                                .exceptionOrNull()
                                ?.message
                                ?: "No se pudo cargar el progreso"
                    )
                }

                return@launch
            }


            val progress =
                progressResult.getOrThrow()


            val streak =
                streakResult.getOrElse {
                    0
                }


            _uiState.update { current ->

                current.copy(

                    points =
                        progress.points,

                    level =
                        progress.level,

                    metrics =
                        ImpactMetricsUiState(

                            waterSavedLiters =
                                progress
                                    .metrics
                                    .waterSavedLiters,

                            co2ReducedKg =
                                progress
                                    .metrics
                                    .co2ReducedKg,

                            wasteReducedKg =
                                progress
                                    .metrics
                                    .wasteReducedKg
                        ),

                    badges =
                        emptyList(),

                    streak =
                        streak,

                    isLoading =
                        false,

                    error =
                        null
                )
            }
        }
    }
}