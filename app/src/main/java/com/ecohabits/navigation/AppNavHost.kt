package com.ecohabits.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ecohabits.presentation.auth.login.LoginRoute
import com.ecohabits.presentation.auth.signin.SignInRoute
import com.ecohabits.presentation.auth.welcome.WelcomeScreen
import com.ecohabits.presentation.challenges.ChallengesRoute
import com.ecohabits.presentation.home.HomeRoute
import com.ecohabits.presentation.progress.ProgressRoute
import com.ecohabits.presentation.startup.SplashScreen
import com.ecohabits.presentation.startup.StartupDestination
import com.ecohabits.presentation.startup.StartupViewModel


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NavigationViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()


    /*
     * Navegación normal de EcoHabits.
     *
     * IMPORTANTE:
     * Mientras estamos en "startup" no dejamos que
     * NavigationViewModel nos mande prematuramente
     * al login/welcome.
     */
    LaunchedEffect(state.currentRoute) {

        val currentRoute =
            navController
                .currentBackStackEntry
                ?.destination
                ?.route


        if (
            currentRoute != "startup" &&
            currentRoute != state.currentRoute
        ) {

            navController.navigate(
                state.currentRoute
            ) {

                /*
                 * Si entramos a una de las pantallas
                 * principales limpiamos las pantallas
                 * de autenticación anteriores.
                 */
                if (
                    state.currentRoute in listOf(
                        "home",
                        "challenges",
                        "progress"
                    )
                ) {

                    popUpTo(
                        navController.graph.startDestinationId
                    ) {

                        inclusive = true
                    }
                }

                launchSingleTop = true

                restoreState = true
            }
        }
    }


    /*
     * Ahora SIEMPRE arrancamos por el splash.
     *
     * Ya no arrancamos directamente por login,
     * welcome o home.
     */
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "startup"
    ) {


        /*
         * ------------------------------------------------
         * STARTUP / SPLASH
         * ------------------------------------------------
         */
        composable(
            route = "startup"
        ) {

            val startupViewModel:
                    StartupViewModel =
                hiltViewModel()


            val startupState by
            startupViewModel
                .uiState
                .collectAsState()


            SplashScreen(
                uiState = startupState
            )


            LaunchedEffect(
                startupState.destination
            ) {

                when (
                    startupState.destination
                ) {

                    StartupDestination.Loading -> {

                        /*
                         * Nos quedamos mostrando
                         * el Splash.
                         */
                    }


                    StartupDestination.Authenticated -> {

                        /*
                         * Hay sesión válida.
                         *
                         * Actualizamos también
                         * NavigationViewModel para
                         * mantener toda la navegación
                         * sincronizada.
                         */
                        viewModel.selectRoute(
                            "home"
                        )


                        navController.navigate(
                            "home"
                        ) {

                            popUpTo(
                                "startup"
                            ) {

                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }


                    StartupDestination.Unauthenticated -> {

                        /*
                         * No existe sesión.
                         *
                         * Mandamos al usuario al Welcome.
                         */
                        viewModel.selectRoute(
                            "welcome"
                        )


                        navController.navigate(
                            "welcome"
                        ) {

                            popUpTo(
                                "startup"
                            ) {

                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                }
            }
        }


        /*
         * ------------------------------------------------
         * AUTH
         * ------------------------------------------------
         */

        composable(
            route = "welcome"
        ) {

            WelcomeScreen(

                onLoginClick = {

                    viewModel.selectRoute(
                        "login"
                    )
                },

                onRegisterClick = {

                    viewModel.selectRoute(
                        "signin"
                    )
                }
            )
        }


        composable(
            route = "login"
        ) {

            LoginRoute(

                onLoginSuccess = {

                    viewModel.selectRoute(
                        "home"
                    )
                },

                onRegisterClick = {

                    viewModel.selectRoute(
                        "signin"
                    )
                }
            )
        }


        composable(
            route = "signin"
        ) {

            SignInRoute(

                onNavigateToHome = {

                    viewModel.selectRoute(
                        "home"
                    )
                },

                onNavigateBack = {

                    viewModel.selectRoute(
                        "welcome"
                    )
                }
            )
        }


        /*
         * ------------------------------------------------
         * MAIN
         * ------------------------------------------------
         */

        composable(
            route = "home"
        ) {

            HomeRoute()
        }


        composable(
            route = "challenges"
        ) {

            ChallengesRoute()
        }


        composable(
            route = "progress"
        ) {

            ProgressRoute()
        }
    }
}