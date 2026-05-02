package com.ecohabits.presentation.progress

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.components.EcoCard
import com.ecohabits.ui.theme.EcoHabitsTheme
import com.ecohabits.ui.theme.EcoHabitsTypography

@Composable
fun ProgressScreen() {
    Column() {

    }
    ProgressTitle()
    StatsCards(streak = "16", points = "2000", "3")
    ImpactBox(savedWater = "45", co2Reduction = "12.5", avoidedWaste = "8.2")
}

@Composable
fun ProgressTitle() {
    EcoCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "Resumen del Progreso",
                style = EcoHabitsTypography().headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )

            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = "Eco icon",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(50.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun StatsCards(streak: String, points: String, badges: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp)
            .height(180.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        StreakCard(streak)
        PointsCard(points)
        BadgesCard(badges)
    }
}

@Composable
fun StreakCard(streak: String) {
    EcoCard(
        modifier = Modifier.padding(horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Streak Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xE2F4511E)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = streak,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Racha días",
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun PointsCard(points: String) {
    EcoCard(
        modifier = Modifier.padding(horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                imageVector = Icons.Default.AutoGraph,
                contentDescription = "Streak Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF1E88E5)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = points,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Puntos",
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun BadgesCard(badges: String) {
    EcoCard(
        modifier = Modifier.padding(horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = "Streak Icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF5A1EE5)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = badges,
                style = EcoHabitsTypography().displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Insignias",
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun ImpactBox(savedWater: String, co2Reduction: String, avoidedWaste: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 320.dp)
            .padding(horizontal = 15.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(3.dp)

    ) {
        Text(
            text = "Impacto Ambiental",
            textAlign = TextAlign.Center,
            style = EcoHabitsTypography().titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 10.dp)
        )
        WaterImpact(savedWater)
        Co2Impact(co2Reduction)
        WasteImpact(avoidedWaste)
    }
}

@Composable
fun WaterImpact(savedWater: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x2036BDAD)
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Water,
                tint = Color(0xFF10BBAD),
                contentDescription = "Water Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column() {
                Text(
                    text = "Agua ahorrada",
                    style = MaterialTheme.typography.titleMedium
                )
                Row{
                    Text(
                        text = savedWater,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF109BBB)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "Litros"
                    )
                }
            }
        }
    }
}


@Composable
fun Co2Impact(co2Reduction: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x3C7CB342)
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Eco,
                tint = Color(0xE83EA743),
                contentDescription = "CO2 Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column() {
                Text(
                    text = "CO2 reducido",
                    style = MaterialTheme.typography.titleMedium
                )
                Row{
                    Text(
                        text = co2Reduction,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xE83EA743)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "kg"
                    )
                }
            }
        }
    }
}

@Composable
fun WasteImpact(avoidedWaste: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .padding(bottom = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x43886B60)
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                tint = Color(0xFFFFFFFF),
                contentDescription = "Trash Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column() {
                Text(
                    text = "Residuos evitados",
                    style = MaterialTheme.typography.titleMedium
                )
                Row{
                    Text(
                        text = avoidedWaste,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF886B60)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "kg"
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewProgressScreen() {
    EcoHabitsTheme {
        ProgressScreen()
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDarkProgressScreen() {
    EcoHabitsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProgressScreen()

        }
    }
}