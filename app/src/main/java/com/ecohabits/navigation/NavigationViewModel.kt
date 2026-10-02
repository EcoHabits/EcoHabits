package com.ecohabits.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.AuthSessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            NavigationState()
        )

    val state: StateFlow<NavigationState> =
        _state.asStateFlow()

    init {
        observeAuthStatus()
    }

    private fun observeAuthStatus() {

        authRepository.authState
            .onEach { authState ->

                when (authState) {

                    AuthSessionState.Loading -> {
                        /*
                         * No cambiamos de ruta.
                         *
                         * El AppNavHost mantiene
                         * "startup" mientras Supabase
                         * restaura la sesión.
                         */
                    }

                    AuthSessionState.Authenticated -> {

                        _state.update {
                            it.copy(
                                currentRoute = "home"
                            )
                        }
                    }

                    AuthSessionState.Unauthenticated -> {

                        _state.update {
                            it.copy(
                                currentRoute = "welcome"
                            )
                        }
                    }
                }
            }
            .launchIn(
                viewModelScope
            )
    }

    fun selectRoute(
        route: String
    ) {

        _state.update {
            it.copy(
                currentRoute = route
            )
        }
    }
}