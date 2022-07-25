package com.movieapp.repository

import com.movieapp.model.Data
import com.movieapp.utils.NetworkResponse
import com.movieapp.utils.Resource

interface MoviesRepository {
    suspend fun getPopularMovies(apiKey: String, page: Int) : NetworkResponse<Data>
    suspend fun getSimilarMoviesById(apiKey: String, page: Int, movieId: Int) : NetworkResponse<Data>
    suspend fun getMoviesByKeyword(apiKey: String, keyword: String, page: Int) : NetworkResponse<Data>
}