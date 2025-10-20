package com.example.filmrecommendation.data.repository

import com.example.filmrecommendation.data.api.MovieApi
import com.example.filmrecommendation.data.model.MovieDetail
import com.example.filmrecommendation.data.model.MovieSearchResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val api: MovieApi) {

    suspend fun getMovieDetails(imdbId: String): MovieDetail {
        val response = api.loadMovieDetails(i = imdbId)
        return response
    }

    suspend fun searchMovies(page:String,query: String): MovieSearchResponse {
        return api.searchMovies(page=page,query = query)

    }

}