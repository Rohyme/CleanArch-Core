package com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails

import com.tripl3dev.domain.Entity.MovieDetails
import io.reactivex.Flowable

interface MoviesDetailsRepositoryI {
    fun getMovieDetails(movieId: Int): Flowable<MovieDetails>
    fun toggleMovieInFav(movieId: Int)
}