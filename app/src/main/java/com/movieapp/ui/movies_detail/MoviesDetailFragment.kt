package com.movieapp.ui.movies_detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieapp.adapters.MoviesPagingAdapter
import com.movieapp.databinding.FragmentMoviesDetailBinding
import com.movieapp.extensions.hide
import com.movieapp.extensions.setImage
import com.movieapp.extensions.showSnackBar
import com.movieapp.extensions.visible
import com.movieapp.model.Movie
import com.movieapp.ui.base.BaseFragment
import com.movieapp.utils.Constants.IMAGE_BASE_URL
import com.movieapp.utils.MovieType
import com.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesDetailFragment :
    BaseFragment<FragmentMoviesDetailBinding>(FragmentMoviesDetailBinding::inflate) {

    private val viewModel: MoviesDetailViewModel by viewModels()
    private val args: MoviesDetailFragmentArgs by navArgs()
    private lateinit var moviesAdapter: MoviesPagingAdapter
    private lateinit var movie: Movie

    override fun init() {
        movie = args.movie
        viewModel.getSimilarMoviesById(movie.id)
        fillData(args.movie)
        initRecycler()
        listeners()
    }

    override fun collectors() {
        getSimilarMovies()
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        moviesAdapter.onMovieClicked = { movie ->
            viewModel.getSimilarMoviesById(movie.id)
            fillData(movie)
            binding.rvSimilarMovies.scrollToPosition(0)
            binding.scrollView.smoothScrollTo(0, 0)
        }
    }

    private fun fillData(movie: Movie) {
        with(binding) {
            ivBackground.setImage(IMAGE_BASE_URL.plus(movie.backdropPath ?: movie.posterPath))
            tvTitle.text = movie.name ?: movie.title
            tvDescription.text = movie.overview
        }
    }

    private fun initRecycler() {
        binding.rvSimilarMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        moviesAdapter = MoviesPagingAdapter(MovieType.SIMILAR)
        binding.rvSimilarMovies.adapter = moviesAdapter
    }

    private fun getSimilarMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSimilarMoviesResponse.collect { movie ->
                    when (movie) {
                        is Resource.DataIsFilled -> {
                            binding.rvSimilarMovies.visible()
                            moviesAdapter.submitData(lifecycle, movie.data!!)
                        }
                        is Resource.Loading -> {
                            binding.rvSimilarMovies.hide()
                        }
                    }
                }
            }
        }
    }

}