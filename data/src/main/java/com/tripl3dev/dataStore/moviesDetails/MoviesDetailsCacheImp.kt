package com.tripl3dev.dataStore.moviesDetails

import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsCacheI
import io.reactivex.Single
import javax.inject.Inject

class MoviesDetailsCacheImp @Inject constructor(val dao: MoviesDetailsDao) : MoviesDetailsCacheI {
    override fun toggleIsFavourite(movieId: Int) {
        dao.toggleFavourite(movieId)
    }


    override fun saveMovieDetails(movie: MovieDetails) {
        dao.insertMovie(movie)
    }

    override fun isCached(): Boolean {
        return true
    }

    override fun setLastCacheTime(lastCache: Long) {

    }

    override fun isExpired(): Boolean {
        return false
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetails> {
        return dao.getMovieDetails(movieId)
    }
}