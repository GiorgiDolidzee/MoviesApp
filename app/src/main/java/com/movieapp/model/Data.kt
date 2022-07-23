package com.movieapp.model

import com.google.gson.annotations.SerializedName

data class Data(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
