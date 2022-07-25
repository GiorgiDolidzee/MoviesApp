package com.movieapp.ui.movies_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
class MoviesDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _getSimilarMoviesResponse = MutableStateFlow<Resource<PagingData<Movie>>>(Resource.Loading())
    val getSimilarMoviesResponse : StateFlow<Resource<PagingData<Movie>>> = _getSimilarMoviesResponse

    fun getSimilarMoviesById(movieId: Int) =
        viewModelScope.launch {
            _getSimilarMoviesResponse.emit(Resource.Loading())
            Pager(config = PagingConfig(1), pagingSourceFactory = { MoviesPagingSource(moviesRepository, MovieType.SIMILAR, movieId = movieId) })
                .flow
                .cachedIn(viewModelScope).collect { movies ->
                    _getSimilarMoviesResponse.emit(Resource.DataIsFilled(movies))
                }
        }

}