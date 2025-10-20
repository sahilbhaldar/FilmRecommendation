package com.example.filmrecommendation.ui.movie.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.filmrecommendation.R
import com.example.filmrecommendation.resource.Resource
import com.example.filmrecommendation.ui.common.Animations
import com.example.filmrecommendation.ui.movie.vm.MovieViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import com.example.filmrecommendation.ui.common.CountryFlagsRow

@Composable
fun MovieDetailScreen(imdbID: String, viewModel: MovieViewModel) {
    val detailState = viewModel.movieDetail.collectAsState().value
    var showCountryDialog by remember { mutableStateOf(false) }

    LaunchedEffect(imdbID) {
        if (imdbID.isEmpty()) {
            //on launch default movie to show
            viewModel.fetchMovieDetail("tt0945513")
        } else {
            viewModel.fetchMovieDetail(imdbID)
        }
    }

        when (detailState) {

            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Animations(
                        R.raw.loading_animation,
                        "The curtain is rising… preparing your movie details 🎬"
                    )
                }
            }

            is Resource.Success -> {
                val movie = detailState.data
                if (movie.isResponse) {
                    val countries = movie.country.split(",").map { it.trim() }
                    val imdbRating = movie.imdbRating.toFloatOrNull() ?: 0f
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Showing poster
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(movie.poster)
                                        .crossfade(true)
                                        .build()
                                ),
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        //Title & Year
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "(${movie.year})",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.Gray)
                        )

                        Spacer(Modifier.height(12.dp))

                        //IMDB Rating
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val filledStars = (imdbRating / 2).toInt()
                            val hasHalf = imdbRating % 2 >= 1

                            repeat(5) { index ->
                                if (index < filledStars) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD700),
                                        modifier = Modifier.size(28.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.StarBorder,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD700),
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "${movie.imdbRating}/10",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium, color = Color.White)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        //Countries as flags
                        Text(
                            text = "Countries:",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold,color = Color.Gray)
                        )
                        Spacer(Modifier.height(8.dp))
                        CountryFlagsRow(countries)

                        Spacer(Modifier.height(20.dp))

                        // Plot
                        Text(
                            text = "Plot",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold,color = Color.Gray)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = movie.plot,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    // Small dialog showing country names
                    if (showCountryDialog) {
                        AlertDialog(
                            onDismissRequest = { showCountryDialog = false },
                            confirmButton = {
                                TextButton(onClick = { showCountryDialog = false }) {
                                    Text("Close")
                                }
                            },
                            title = { Text("Countries") },
                            text = { Text(countries.joinToString("\n")) }
                        )
                    }
                } else {
                    //Animation for no data found if response is false
                    Animations(
                        R.raw.no_data,
                        "No data available right now. Please refresh or try another title."
                    )
                }
            }

            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${detailState.message}")
                }
            }
        }
}

//Converts country name → flag emoji using Unicode regional indicators
fun getFlagEmoji(country: String): String {
    val map = mapOf(
        "United States" to "🇺🇸",
        "Canada" to "🇨🇦",
        "France" to "🇫🇷",
        "Germany" to "🇩🇪",
        "India" to "🇮🇳",
        "United Kingdom" to "🇬🇧",
        "Japan" to "🇯🇵",
        "Australia" to "🇦🇺",
        "China" to "🇨🇳",
        "Brazil" to "🇧🇷",
        "Italy" to "🇮🇹",
        "Spain" to "🇪🇸",
        "Mexico" to "🇲🇽",
        "South Korea" to "🇰🇷",
        "Russia" to "🇷🇺",
        "Netherlands" to "🇳🇱",
        "Sweden" to "🇸🇪",
        "Switzerland" to "🇨🇭",
        "South Africa" to "🇿🇦",
        "New Zealand" to "🇳🇿"
    )
    return map[country] ?: "🌍"
}
