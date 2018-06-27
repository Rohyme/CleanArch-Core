package com.tripl3dev.presentation.ui.movieDetails

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    lateinit var viewModel: MovieDetailsVM
    lateinit var binding: ActivityMovieDetailsBinding
    var movieId = 0


    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val FAVOURITE = -30
        const val NOT_FAVOURITE = -31
        const val FAVOURITE_SYNCING = -32

    }


/*
    override fun getActivityVM(): Class<out BaseViewModel> {
        return MoviesVM::class.java
    }
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
//        viewModel = vm as MovieDetailsVM
//        movieId = 383498
//        viewModel.fetchMovieDetails(movieId)
//        setMovieDetails()
//        onFavouriteChanged()
    }

    private fun setMovieDetails() {
        viewModel.getMovieDetails().observe(this, Observer<Stateview> {
            when (it) {
                is Stateview.Success<*> -> {

                    val data = it.data as MovieDetails
//                    binding.container.setState(StatesConstants.NORMAL_STATE)
                    binding.favourite = data.favourite
                    binding.movie = data
                }

                is Stateview.Failure -> {
                    showError(it.error)
                }

                is Stateview.Loading -> {
                    showLoading()
                }
            }

        })
    }

    private fun showLoading() {
//        binding.container.setState(StatesConstants.LOADING_STATE)
    }

    private fun showError(it: Throwable) {
//        val v = binding.container.setState(StatesConstants.ERROR_STATE)
//        v.findViewById<Button>(R.id.textButton).setOnClickListener {
//            viewModel.retry()
//        }
//        v.findViewById<TextView>(R.id.textError).text = it.message
    }


    fun onAddToFavouriteClicked(v: View) {
//        viewModel.toggleFavourite(movieId, binding.favourite!!)
        Toast.makeText(this@MovieDetailsActivity, "Clicked", Toast.LENGTH_SHORT).show()
    }

    fun onFavouriteChanged() {
        viewModel.movieIsFavourite().observe(this, Observer {
            binding.favourite = it
            Log.e("MoviesDetailsAct", it.toString())
        })
    }


}
