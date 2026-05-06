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
import com.ecohabits.presentation.education.EducationRoute
import com.ecohabits.presentation.home.HomeRoute
import com.ecohabits.presentation.progress.ProgressRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.currentRoute) {
        if (navController.currentBackStackEntry?.destination?.route != state.currentRoute) {
            navController.navigate(state.currentRoute) {
                // Si navegamos a una de las pantallas principales, limpiamos el stack de auth
                if (state.currentRoute in listOf("home", "challenges", "progress")) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onLoginClick = { viewModel.selectRoute("login") },
                onRegisterClick = { viewModel.selectRoute("signin") }
            )
        }
        composable("login") {
            LoginRoute(
                onLoginSuccess = { viewModel.selectRoute("home") },
                onRegisterClick = { viewModel.selectRoute("signin") }
            )
        }
        composable("signin") {
            SignInRoute(
                onNavigateToLogin = { viewModel.selectRoute("login") },
                onNavigateBack = { viewModel.selectRoute("welcome") }
            )
        }
        composable("home") { HomeRoute() }
        composable("challenges") { ChallengesRoute() }
        composable("progress") { ProgressRoute() }
        composable("education") { EducationRoute() }
    }
}
