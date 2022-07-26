package com.movieapp.ui.movies

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieapp.adapters.MoviesPagingAdapter
import com.movieapp.databinding.FragmentMoviesBinding
import com.movieapp.utils.extensions.getErrorMessage
import com.movieapp.utils.extensions.hide
import com.movieapp.utils.extensions.showSnackBar
import com.movieapp.utils.extensions.visible
import com.movieapp.ui.base.BaseFragment
import com.movieapp.utils.MovieType
import com.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesPagingAdapter

    override fun init() {
        viewModel.getMovies()
        initRecycler()
        listeners()
    }

    override fun collectors() {
        getMovies()
    }

    private fun listeners() {
        moviesAdapter.onMovieClicked = {
            val action = MoviesFragmentDirections.actionMoviesFragmentToMoviesDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.etSearch.setText("")
            viewModel.getMovies()
            binding.swipeRefresh.isRefreshing = false
        }
        binding.etSearch.addTextChangedListener { text ->
            viewModel.getMovies(text.toString())
        }
    }

    private fun initRecycler() {
        binding.rvMovies.layoutManager = LinearLayoutManager(context)
        moviesAdapter = MoviesPagingAdapter(MovieType.POPULAR)
        binding.rvMovies.adapter = moviesAdapter
    }

    private fun getMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMoviesResponse.collect { movies ->
                    when (movies) {
                        is Resource.DataIsFilled -> {
                            binding.rvMovies.visible()
                            binding.rvMovies.startLayoutAnimation()
                            moviesAdapter.submitData(lifecycle, movies.data!!)
                            moviesAdapter.addLoadStateListener { loadState ->
                                if (loadState.append is LoadState.Error || loadState.prepend is LoadState.Error || loadState.refresh is LoadState.Error) {
                                    view?.showSnackBar(loadState.getErrorMessage())
                                }
                            }
                        }
                        is Resource.Loading -> {
                            binding.rvMovies.hide()
                        }
                    }
                }
            }
        }
    }


}