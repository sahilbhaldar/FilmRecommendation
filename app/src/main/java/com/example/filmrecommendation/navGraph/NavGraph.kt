package com.example.filmrecommendation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.filmrecommendation.ui.movie.screen.MovieDetailScreen
import com.example.filmrecommendation.ui.movie.screen.MovieListScreen
import com.example.filmrecommendation.ui.movie.vm.MovieViewModel

sealed class Screen(val route: String) {
    object MovieList : Screen("movie_list")
    object MovieDetail : Screen("movie_detail/{imdbID}") {
        fun createRoute(imdbID: String) = "movie_detail/$imdbID"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: MovieViewModel = hiltViewModel()

    NavHost(navController, startDestination = Screen.MovieDetail.route) {
        composable(Screen.MovieList.route) {
            MovieListScreen(navController, viewModel)
        }
        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument("imdbID") { type = NavType.StringType })
        ) { backStackEntry ->
            val imdbID = backStackEntry.arguments?.getString("imdbID") ?: ""
            MovieDetailScreen(imdbID, viewModel)
        }
    }
}
