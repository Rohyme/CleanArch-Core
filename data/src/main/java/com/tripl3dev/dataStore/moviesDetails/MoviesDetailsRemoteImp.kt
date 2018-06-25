package com.tripl3dev.dataStore.moviesDetails

import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsRemoteI
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.domain.service.ApiService
import io.reactivex.Single
import javax.inject.Inject

class MoviesDetailsRemoteImp @Inject constructor(val api: ApiService) : MoviesDetailsRemoteI {
    override fun getMovieDetails(movieId: Int): Single<MovieDetails> {
        return api.getMovieDetails(movieId, Constants.MOVIES_API_TOKEN)
    }

}