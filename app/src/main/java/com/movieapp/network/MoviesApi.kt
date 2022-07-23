package com.movieapp.network

import com.movieapp.model.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/tv/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<Data>


    @GET("/3/tv/{tv_id}/similar")
    suspend fun getSimilarMovies(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<Data>


    @GET("/3/search/movie")
    suspend fun getMoviesByKeyword(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<Data>

}