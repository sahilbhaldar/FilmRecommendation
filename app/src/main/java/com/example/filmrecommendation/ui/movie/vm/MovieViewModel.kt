package com.example.filmrecommendation.ui.movie.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmrecommendation.data.model.Movie
import com.example.filmrecommendation.data.model.MovieDetail
import com.example.filmrecommendation.data.model.MovieDetailResponseByPage
import com.example.filmrecommendation.data.repository.MovieRepository
import com.example.filmrecommendation.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val movies: StateFlow<Resource<List<Movie>>> = _movies

    private val _movieDetail = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading)
    val movieDetail: StateFlow<Resource<MovieDetail>> = _movieDetail

    private var currentPage = 1
    private var currentQuery = ""

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _movies.value = Resource.Loading
            try {
                val response = repository.searchMovies(currentPage.toString(),query)
                _movies.value = Resource.Success(response.search ?: emptyList())
                currentQuery = query
            } catch (e: Exception) {
                _movies.value = Resource.Error(e.message ?: "Error occurred")
            }
        }
    }

    fun fetchMovieDetail(imdbId: String) {
        viewModelScope.launch {
            _movieDetail.value = Resource.Loading
            try {
                val response = repository.getMovieDetails(imdbId = imdbId)
                Log.i("fetchMovieDetail Response:",response.toString())
                _movieDetail.value = Resource.Success(response)
            } catch (e: Exception) {
                _movieDetail.value = Resource.Error(e.message ?: "Error occurred")
                Log.i("fetchMovieDetail Error:",e.message.toString())

            }
        }
    }

    fun changePage(isNext: Boolean) {
        currentPage = if (isNext) currentPage + 1 else maxOf(1, currentPage - 1)
        searchMovies(currentQuery)
    }

    fun getCurrentPage() = currentPage
}