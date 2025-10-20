package com.example.filmrecommendation.ui.movie.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.filmrecommendation.R
import com.example.filmrecommendation.data.model.Movie
import com.example.filmrecommendation.navGraph.Screen
import com.example.filmrecommendation.ui.movie.vm.MovieViewModel
import com.example.filmrecommendation.resource.Resource
import com.example.filmrecommendation.ui.common.Animations

@Composable
fun MovieListScreen(navController: NavController, viewModel: MovieViewModel) {

    val TAG = "MovieListScreen"
    val state by viewModel.movies.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2F), Color(0xFF2A2A3D))
                )
            )
            .padding(16.dp)
    ) {
        when (state) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Animations(
                            R.raw.loading_animation,
                            "Searching for your movie... Hold on"
                        )
                    }
                }
            }

            is Resource.Success -> {
                val movies = (state as Resource.Success<List<Movie>>).data
                // Creating Lazy list
                if (!movies.isEmpty()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(movies) { movie ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable { navController.navigate(Screen.MovieDetail.createRoute(movie.imdbID)) },
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                ) {
                                    // Poster Image
                                    Image(
                                        painter = rememberAsyncImagePainter(movie.poster),
                                        contentDescription = movie.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )

                                    // Gradient overlay for readability
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(Color.Transparent, Color(0xAA000000)),
                                                    startY = 50f
                                                )
                                            )
                                    )

                                    // Text info overlay
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(12.dp)
                                    ) {
                                        Text(
                                            text = movie.title,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Year: ${movie.year} | ${movie.type.capitalize()}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
                else{
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Animations(
                            R.raw.no_data,
                            "Hmm… nothing came up. Maybe try a different movie name?"
                        )
                    }
                }
            }

            is Resource.Error -> {
                Log.e("$TAG, :Resource.Error : ", (state as Resource.Error).message)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Animations(
                            R.raw.no_data,
                            "Something went wrong, try again..."
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        //page numbers
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.changePage(isNext = false) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF))
            ) { Text("←", color = Color.White) }

            Text("Page: ${viewModel.getCurrentPage()}", color = Color.White)

            Button(
                onClick = { viewModel.changePage(isNext = true) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF))
            ) { Text("→", color = Color.White) }
        }
        Spacer(Modifier.height(8.dp))
    }
}
