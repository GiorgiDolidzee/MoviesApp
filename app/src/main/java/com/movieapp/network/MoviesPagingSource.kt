package com.movieapp.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movieapp.BuildConfig
import com.movieapp.model.Data
import com.movieapp.model.Movie
import com.movieapp.repository.MoviesRepository
import com.movieapp.utils.MovieType
import com.movieapp.utils.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesPagingSource(
    private val popularMoviesRepository: MoviesRepository,
    private val movieType: MovieType,
    private val keyword: String?=null,
    private val movieId: Int?=null
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val page = params.key ?: 1
                val response : NetworkResponse<Data> = when(movieType) {
                    MovieType.POPULAR -> popularMoviesRepository.getPopularMovies(BuildConfig.API_KEY, page)
                    MovieType.SIMILAR -> popularMoviesRepository.getSimilarMoviesById(BuildConfig.API_KEY, page, movieId?:0)
                    MovieType.SEARCH -> popularMoviesRepository.getMoviesByKeyword(BuildConfig.API_KEY, keyword?:"", page)
                }

                when(response) {
                    is NetworkResponse.Success -> {
                        var previousPage: Int? = null
                        var nextPage: Int? = null
                        if (response.data?.totalPages!! > page) { nextPage = page + 1 }
                        if (page != 1) { previousPage = page - 1 }
                        LoadResult.Page(
                            data = response.data.results,
                            prevKey = previousPage,
                            nextKey = nextPage
                        )
                    }
                    is NetworkResponse.Error -> {
                        LoadResult.Error(Exception("Something went wrong.. Try again"))
                    }
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }


}