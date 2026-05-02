package com.ecohabits

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ecohabits.navigation.AppNavHost
import com.ecohabits.ui.screens.LoginScreen
import com.ecohabits.ui.screens.SignInScreen
import com.ecohabits.ui.theme.EcoHabitsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoHabitsTheme {
                LoginScreen(
                    onLogin = {},
                    onRegisterClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    EcoHabitsTheme {
        LoginScreen(onLogin = {}, onRegisterClick = {})
    }
}

@Preview
@Composable
fun AppNavHostPreview() {
    EcoHabitsTheme {
        SignInScreen(onSignIn = {}, onBackClick = {})
    }
}
