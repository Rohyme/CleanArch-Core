package com.tripl3dev.dataStore.moviesDetails

import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsRepositoryI
import io.reactivex.Flowable
import javax.inject.Inject

class MovieDetailsRepositoryImp @Inject constructor(val factory: MoviesDetailsDataStoreFactory) : MoviesDetailsRepositoryI {

    override fun getMovieDetails(movieId: Int): Flowable<MovieDetails> {
        return Flowable.concatArrayDelayError(factory.retrieveCasheDataStore().getMovieDetails(movieId).toFlowable(),
                factory.retrieveDataStore().getMovieDetails(movieId).toFlowable().doOnNext {
                    factory.retrieveCasheDataStore().saveMovieDetails(it)
                }).firstElement().toFlowable()
    }


}