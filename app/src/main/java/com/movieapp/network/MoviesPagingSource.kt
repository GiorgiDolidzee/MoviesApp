package com.movieapp.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movieapp.model.Data
import com.movieapp.model.Movie
import com.movieapp.repositories.popular_movies.MoviesRepository
import com.movieapp.utils.Constants.API_KEY
import com.movieapp.utils.MovieType
import com.movieapp.utils.Resource
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
                val response : Resource<Data> = when(movieType) {
                    MovieType.POPULAR -> popularMoviesRepository.getPopularMovies(API_KEY, page)
                    MovieType.SIMILAR -> popularMoviesRepository.getSimilarMoviesById(API_KEY, page, movieId!!)
                    MovieType.SEARCH -> popularMoviesRepository.getMoviesByKeyword(API_KEY, keyword!!, page)
                }

                when(response) {
                    is Resource.Success -> {
                        var previousPage: Int? = null
                        var nextPage: Int? = null
                        if (response.data?.totalPages!! > page) { nextPage = page + 1 }
                        if (page != 1) { previousPage = page - 1 }
                        LoadResult.Page(
                            response.data.results,
                            previousPage,
                            nextPage
                        )
                    }
                    is Resource.Error -> {
                        LoadResult.Error(Exception())
                    }
                    else -> {
                        LoadResult.Error(Exception())
                    }
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }


}