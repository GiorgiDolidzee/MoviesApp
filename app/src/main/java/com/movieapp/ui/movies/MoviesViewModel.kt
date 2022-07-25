package com.movieapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.movieapp.model.Movie
import com.movieapp.network.MoviesPagingSource
import com.movieapp.repositories.popular_movies.MoviesRepository
import com.movieapp.utils.MovieType
import com.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _getMoviesResponse = MutableStateFlow<Resource<PagingData<Movie>>>(Resource.Loading())
    val getMoviesResponse : StateFlow<Resource<PagingData<Movie>>> = _getMoviesResponse

    fun getMovies(keyword: String?=null) =
        viewModelScope.launch {
            _getMoviesResponse.emit(Resource.Loading())
            when(keyword) {
                null, "" -> {
                    Pager(config = PagingConfig(1), pagingSourceFactory = { MoviesPagingSource(moviesRepository, MovieType.POPULAR, keyword) })
                        .flow
                        .cachedIn(viewModelScope).collect { movies ->
                            _getMoviesResponse.emit(Resource.DataIsFilled(movies))
                        }
                }
                else -> {
                    Pager(config = PagingConfig(1), pagingSourceFactory = { MoviesPagingSource(moviesRepository, MovieType.SEARCH, keyword) })
                        .flow
                        .cachedIn(viewModelScope).collect { movies ->
                            _getMoviesResponse.emit(Resource.DataIsFilled(movies))
                        }
                }
            }
        }

}