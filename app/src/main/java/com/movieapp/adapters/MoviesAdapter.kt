package com.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.databinding.ItemMovieBinding
import com.movieapp.databinding.ItemSimilarMovieBinding
import com.movieapp.model.Movie
import com.movieapp.utils.Constants.IMAGE_BASE_URL
import com.movieapp.utils.MovieType
import com.movieapp.utils.extensions.setImage

typealias onMovieClick = (movie: Movie) -> Unit

class MoviesPagingAdapter(val movieType: MovieType) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(
    DiffCallBack()
) {
    lateinit var onMovieClicked: onMovieClick

    inner class MoviesViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val movie = getItem(absoluteAdapterPosition)
            with(binding) {
                ivImage.setImage(IMAGE_BASE_URL.plus(movie?.posterPath))
                tvVoteAverage.text = movie?.voteAverage.toString()
                tvTitle.text = movie?.name?: movie?.title
            }

            binding.root.setOnClickListener {
                if (movie != null) {
                    onMovieClicked(movie)
                }
            }
        }
    }

    inner class SimilarMoviesViewHolder(val binding: ItemSimilarMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val movie = getItem(absoluteAdapterPosition)
            with(binding) {
                ivImage.setImage(IMAGE_BASE_URL.plus(movie?.posterPath))
            }

            binding.root.setOnClickListener {
                if (movie != null) {
                    onMovieClicked(movie)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MoviesViewHolder -> holder.bind()
            is SimilarMoviesViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(movieType) {
            MovieType.SIMILAR -> SimilarMoviesViewHolder(
                ItemSimilarMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                    )
                )
            MovieType.POPULAR -> MoviesViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                    )
                )
            else -> MoviesViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

}