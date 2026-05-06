package com.ecohabits.presentation.education

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecohabits.ui.components.EcoButton
import com.ecohabits.ui.components.EcoTextField

@Composable
fun EducationScreen(uiState: EducationUiState, onSearchQueryChange: (String) -> Unit){

    Column(modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        EducationSearchInput(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChange
        )

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp))
        {
            items(uiState.articles){ article ->
                ArticleItem(article = article)
            }
        }
    }
}

@Composable
private fun EducationSearchInput(value: String, onValueChange: (String) -> Unit) {

    EcoTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Buscar Articulos"
    )
}

@Composable
private fun ArticleItem(article: EducationArticle) {

    Card(modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp))
    {
        Column(modifier = Modifier.padding(16.dp))
        {
            Text(text = article.title)
            Text(text = article.summary)

            EcoButton(text = "Leer mas",
                onClick = {})
        }
    }

}