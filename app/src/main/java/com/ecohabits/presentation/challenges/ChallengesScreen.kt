package com.ecohabits.presentation.challenges

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.navigation.AppNavHost
import com.ecohabits.ui.theme.EcoHabitsTheme

// Setting the wallpaper for the screen
@Composable
fun AtomWallpaper(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    EcoHabitsTheme(darkTheme = darkTheme) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}
// Setting the atom for the
@Composable
fun AtomChallengueUppBar(modifier: Modifier = Modifier) {
    // Declarar modifier: Modifier = Modifier hace que el organismo (conjunto de atomos) le indique a este atomo como comportarse
    Column {
        Text(text = "Desafios del Dia", modifier = modifier)
    }
}
// Final Screen
@Composable
fun ChallengesScreen(){
    AtomWallpaper(modifier = Modifier.fillMaxSize()) {
        AtomChallengueUppBar()
    }
}