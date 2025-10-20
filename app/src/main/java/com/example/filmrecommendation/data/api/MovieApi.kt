package com.example.filmrecommendation.data.api

import com.example.filmrecommendation.data.model.MovieDetail
import com.example.filmrecommendation.data.model.MovieSearchResponse
import com.example.movierecommendation.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String = Constants.apikey,
        @Query("page") page:String,
        @Query("s") query: String
    ): MovieSearchResponse

    @GET("/")
    suspend fun loadMovieDetails(
        @Query("apikey") apiKey: String = Constants.apikey,
        @Query("plot") plot: String = "full",
        @Query("i") i: String
    ): MovieDetail
}