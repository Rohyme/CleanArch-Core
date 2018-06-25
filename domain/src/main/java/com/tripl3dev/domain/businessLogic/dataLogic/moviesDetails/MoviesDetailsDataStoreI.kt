package com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails

import com.tripl3dev.domain.Entity.MovieDetails
import io.reactivex.Single

interface MoviesDetailsDataStoreI {
    fun getMovieDetails(movieId: Int): Single<MovieDetails>
}