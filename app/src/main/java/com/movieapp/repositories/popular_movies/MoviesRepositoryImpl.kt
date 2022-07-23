package com.movieapp.repositories.popular_movies

import com.movieapp.model.Data
import com.movieapp.network.MoviesApi
import com.movieapp.utils.Resource
import com.movieapp.utils.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val api: MoviesApi,
) : MoviesRepository {

    override suspend fun getPopularMovies(apiKey: String, page: Int): Resource<Data> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = api.getPopularMovies(apiKey = apiKey, page = page)
                val result = response.body()
                when (response.code()) {
                    SUCCESS_CODE -> responseHandler.handleSuccess(result)
                    else -> Resource.Error("Network error, code: ${response.code()}")
                }
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

    override suspend fun getSimilarMoviesById(apiKey: String, page: Int, movieId: Int): Resource<Data> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = api.getSimilarMovies(tvId = movieId, apiKey = apiKey, page = page)
                val result = response.body()
                when (response.code()) {
                    SUCCESS_CODE -> responseHandler.handleSuccess(result)
                    else -> Resource.Error("Network error, code: ${response.code()}")
                }
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

    override suspend fun getMoviesByKeyword(
        apiKey: String,
        keyword: String,
        page: Int
    ): Resource<Data> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = api.getMoviesByKeyword(apiKey = apiKey, query = keyword, page = page)
                val result = response.body()
                when (response.code()) {
                    SUCCESS_CODE -> responseHandler.handleSuccess(result)
                    else -> Resource.Error("Network error, code: ${response.code()}")
                }
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

    companion object {
        const val SUCCESS_CODE = 200
    }

}