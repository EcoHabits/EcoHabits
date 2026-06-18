package com.ecohabits.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ecohabits.navigation.AppNavHost
import com.ecohabits.navigation.NavItems
import com.ecohabits.navigation.NavigationViewModel
import com.ecohabits.ui.components.BottomNavigationBar
import com.ecohabits.ui.components.LocationPermissionHandler
import com.ecohabits.ui.theme.EcoHabitsTheme

@Composable
fun MainScreen() {
    EcoHabitsTheme {
        val navController = rememberNavController()
        val viewModel: NavigationViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        val navItems = NavItems.bottomNavItems

        // se determina si la navbar se debe mostrar
        val showBottomBar = state.currentRoute in navItems.map { it.route }

        val selectedIndex = navItems.indexOfFirst { it.route == state.currentRoute }.coerceAtLeast(0)

        // Se llama al manejador de permisos
        LocationPermissionHandler(onPermissionsDenied = {

            }
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(
                        selectedItem = selectedIndex,
                        onItemSelected = { index ->
                            viewModel.selectRoute(navItems[index].route)
                        },
                        items = navItems
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}