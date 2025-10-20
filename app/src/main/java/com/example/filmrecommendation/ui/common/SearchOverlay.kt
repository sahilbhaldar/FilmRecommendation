package com.example.filmrecommendation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.filmrecommendation.ui.movie.vm.MovieViewModel
import com.example.filmrecommendation.ui.theme.FilmRecommendationTheme

@Composable
fun SearchOverlay(
    viewModel: MovieViewModel,
    navController: NavController,
    onDismiss: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    FilmRecommendationTheme {

    Surface(
        modifier = Modifier
            .height(56.dp) //height
            .fillMaxWidth() //adjustable width
            .background(MaterialTheme.colorScheme.surface),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 2.dp).fillMaxWidth()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search movie") },
                singleLine = true,
                modifier = Modifier.weight(1f).height(56.dp).align(Alignment.CenterVertically),
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                }
            )

            Button(
                onClick = {
                    if (query.isNotBlank()) {
                        navController.navigate("movie_list")
                        viewModel.searchMovies(query.trim())
                        onDismiss()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Go")
            }
        }
    }
}
}