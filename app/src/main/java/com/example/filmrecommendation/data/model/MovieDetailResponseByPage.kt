package com.example.filmrecommendation.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponseByPage(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Response") val response: Boolean
)