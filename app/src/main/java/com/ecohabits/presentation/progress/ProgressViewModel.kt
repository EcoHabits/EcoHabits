package com.ecohabits.presentation.progress

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.model.Badge
import com.ecohabits.domain.usecase.CheckAndUnlockBadgesUseCase
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
    private val getUserStreakUseCase: GetUserStreakUseCase,
    private val checkAndUnlockBadgesUseCase: CheckAndUnlockBadgesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            ProgressUiState()
        )

    val uiState: StateFlow<ProgressUiState> =
        _uiState.asStateFlow()

    /*
     * Cola temporal de insignias recién desbloqueadas.
     *
     * NO se guarda en Supabase ni Room.
     * Solo sirve para mostrar las animaciones una por una.
     */
    private val badgeUnlockQueue =
        ArrayDeque<BadgeUiState>()

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

            val badgeResult =
                checkAndUnlockBadgesUseCase()

            if (badgeResult.isFailure) {

                android.util.Log.e(
                    "BADGES",
                    "Error cargando insignias",
                    badgeResult.exceptionOrNull()
                )
            }

            val badgeCheck =
                badgeResult.getOrNull()

            val badgeUiStates =
                badgeCheck
                    ?.badges
                    ?.map { badge ->
                        badge.toUiState()
                    }
                    ?: emptyList()

            /*
             * Convertimos TODAS las insignias recién
             * obtenidas y las ponemos en la cola.
             */
            val newlyUnlockedBadges =
                badgeCheck
                    ?.newlyUnlockedBadges
                    ?.map { badge ->
                        badge.toUiState()
                    }
                    ?: emptyList()

            badgeUnlockQueue.clear()

            newlyUnlockedBadges.forEach { badge ->
                badgeUnlockQueue.addLast(
                    badge
                )
            }

            val firstBadge =
                badgeUnlockQueue
                    .firstOrNull()

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
                        badgeUiStates,

                    streak =
                        streak,

                    newlyUnlockedBadge =
                        firstBadge,

                    isLoading =
                        false,

                    error =
                        null
                )
            }
        }
    }

    /*
     * Cuando el usuario pulsa "Continuar":
     *
     * 1. quitamos la insignia actual
     * 2. mostramos automáticamente la siguiente
     * 3. si ya no quedan, cerramos el overlay
     */
    fun dismissBadgeUnlock() {

        if (badgeUnlockQueue.isNotEmpty()) {
            badgeUnlockQueue.removeFirst()
        }

        val nextBadge =
            badgeUnlockQueue
                .firstOrNull()

        _uiState.update {
            it.copy(
                newlyUnlockedBadge =
                    nextBadge
            )
        }
    }

    private fun Badge.toUiState(): BadgeUiState {

        val visual =
            when (code) {

                "FIRST_STEP" ->
                    BadgeVisual(
                        icon =
                            Icons.Default.Eco,
                        color =
                            Color(0xFF43A047)
                    )

                "ECO_LEARNER" ->
                    BadgeVisual(
                        icon =
                            Icons.Default.AutoAwesome,
                        color =
                            Color(0xFF66BB6A)
                    )

                "ECO_WARRIOR" ->
                    BadgeVisual(
                        icon =
                            Icons.Default.WorkspacePremium,
                        color =
                            Color(0xFF2E7D32)
                    )

                "GREEN_STAR" ->
                    BadgeVisual(
                        icon =
                            Icons.Default.Star,
                        color =
                            Color(0xFFF9A825)
                    )

                "GREEN_STREAK" ->
                    BadgeVisual(
                        icon =
                            Icons.Default.LocalFireDepartment,
                        color =
                            Color(0xFFFF7043)
                    )

                "UNSTOPPABLE" ->
                    BadgeVisual(
                        icon =
                            Icons.Default.WorkspacePremium,
                        color =
                            Color(0xFF7E57C2)
                    )

                else ->
                    BadgeVisual(
                        icon =
                            Icons.Default.Eco,
                        color =
                            Color(0xFF43A047)
                    )
            }

        return BadgeUiState(
            id = id,
            title = name,
            description = description,
            icon = visual.icon,
            backgroundColor = visual.color,
            unlocked = unlocked
        )
    }

    private data class BadgeVisual(
        val icon:
        androidx.compose.ui.graphics.vector.ImageVector,
        val color: Color
    )
}