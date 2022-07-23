package com.movieapp.repositories.popular_movies

import com.movieapp.model.Data
import com.movieapp.utils.Resource

interface MoviesRepository {
    suspend fun getPopularMovies(apiKey: String, page: Int) : Resource<Data>
    suspend fun getSimilarMoviesById(apiKey: String, page: Int, movieId: Int) : Resource<Data>
    suspend fun getMoviesByKeyword(apiKey: String, keyword: String, page: Int) : Resource<Data>
}