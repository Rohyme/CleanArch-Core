package com.tripl3dev.presentation.ui.movieDetails

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseActivityWithInjector
import com.tripl3dev.presentation.base.BaseViewModel
import com.tripl3dev.presentation.base.getException
import com.tripl3dev.presentation.databinding.ActivityMovieDetailsBinding
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState

class MovieDetailsActivity : BaseActivityWithInjector() {
    lateinit var viewModel: MovieDetailsVM
    lateinit var binding: ActivityMovieDetailsBinding
    var movieId = 0


    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val FAVOURITE = -30
        const val NOT_FAVOURITE = -31
        const val FAVOURITE_SYNCING = -32

    }


    override fun getActivityVM(): Class<out BaseViewModel> {
        return MovieDetailsVM::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        viewModel = vm as MovieDetailsVM
        movieId = intent.getIntExtra(MOVIE_ID, 0)
        viewModel.fetchMovieDetails(movieId)
        setMovieDetails()
        onFavouriteChanged()
    }

    private fun setMovieDetails() {
        viewModel.getMovieDetails().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {

                    val data = it.data as MovieDetails
                    binding.container.setState(StatesConstants.NORMAL_STATE)
                    binding.favourite = data.favourite
                    binding.movie = data
                }

                is Stateview.Failure -> {
                    showError(it.error.getException())
                }

                is Stateview.Loading -> {
                    showLoading()
                }
            }

        })
    }

    private fun showLoading() {
        binding.container.setState(StatesConstants.LOADING_STATE)
    }

    private fun showError(it: Throwable) {
        val v = binding.container.setState(StatesConstants.ERROR_STATE)
        v.findViewById<Button>(R.id.textButton).setOnClickListener {
            viewModel.retry()
        }
        v.findViewById<TextView>(R.id.textError).text = it.message
    }


    fun onAddToFavouriteClicked(v: View) {
        viewModel.toggleFavourite(movieId, binding.favourite!!)
    }

    fun onFavouriteChanged() {
        viewModel.movieIsFavourite().observe(this, Observer {
            binding.favourite = it
            Log.e("MoviesDetailsAct", it.toString())
        })
    }


}
