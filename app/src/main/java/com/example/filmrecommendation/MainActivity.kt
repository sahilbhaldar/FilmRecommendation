package com.example.filmrecommendation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.filmrecommendation.navGraph.NavGraph
import com.example.filmrecommendation.ui.common.SearchOverlay
import com.example.filmrecommendation.ui.movie.vm.MovieViewModel
import com.example.filmrecommendation.ui.theme.FilmRecommendationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmRecommendationTheme {
                val navController = rememberNavController()
                val showSearchField = remember { mutableStateOf(false) }
                val movieViewModel: MovieViewModel = viewModel()

                Column(modifier = Modifier.fillMaxSize()) {

                    //Top search row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .statusBarsPadding()
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { showSearchField.value = !showSearchField.value }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }

                        // Animated sliding search field
                        AnimatedVisibility(
                            visible = showSearchField.value,
                            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                        ) {
                            SearchOverlay(
                                viewModel = movieViewModel,
                                navController = navController,
                                onDismiss = { showSearchField.value = false }
                            )
                        }
                    }
                    //Reset here
                    Box(modifier = Modifier.weight(1f)
                        .background(MaterialTheme.colorScheme.primary)) {
                        NavGraph(navController)
                    }
                }
            }
        }
    }
}

