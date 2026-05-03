package com.ecohabits.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecohabits.presentation.auth.login.LoginRoute
import com.ecohabits.presentation.auth.signin.SignInScreen
import com.ecohabits.presentation.challenges.ChallengesRoute
import com.ecohabits.presentation.home.HomeRoute
import com.ecohabits.presentation.progress.ProgressRoute

private const val LOGIN_ROUTE = "login"
private const val SIGNIN_ROUTE = "signin"
private const val HOME_ROUTE = "home"
private const val CHALLENGES_ROUTE = "challenges"
private const val PROGRESS_ROUTE = "progress"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {
        composable(LOGIN_ROUTE) {
            LoginRoute(
                onNavigateToHome = {
                    navController.navigate(HOME_ROUTE) {
                        popUpTo(LOGIN_ROUTE) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(SIGNIN_ROUTE)
                }
            )
        }
        composable(SIGNIN_ROUTE) {
            SignInScreen(
                onSignIn = {
                    navController.navigate(HOME_ROUTE) {
                        popUpTo(LOGIN_ROUTE) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(HOME_ROUTE) { HomeRoute() }
        composable(CHALLENGES_ROUTE) { ChallengesRoute() }
        composable(PROGRESS_ROUTE) { ProgressRoute() }
    }
}
