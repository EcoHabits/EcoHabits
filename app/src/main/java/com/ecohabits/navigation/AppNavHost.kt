package com.ecohabits.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecohabits.presentation.challenges.ChallengesRoute
import com.ecohabits.presentation.home.HomeRoute
import com.ecohabits.presentation.progress.ProgressRoute

private const val HOME_ROUTE = "home"
private const val CHALLENGES_ROUTE = "challenges"
private const val PROGRESS_ROUTE = "progress"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(HOME_ROUTE) { HomeRoute() }
        composable(CHALLENGES_ROUTE) { ChallengesRoute() }
        composable(PROGRESS_ROUTE) { ProgressRoute() }
    }
}

