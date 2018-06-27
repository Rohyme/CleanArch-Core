package com.tripl3dev.dataStore.movies

import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesRepositoryI
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImp @Inject constructor(val factory: MoviesDataStoreFactory) : MoviesRepositoryI {
    companion object {
        const val POPULAR_MOVIES = -10
        const val TOP_RATED_MOVIES = -12
        const val LATEST_MOVIES = -11
    }

    override fun getLatestMovies(pageNum: Int): Flowable<MoviesEntity> {
        return Flowable.concatArrayDelayError(factory.retrieveCasheDataStore().getLatestMovies(pageNum).toFlowable(),
                factory.retrieveRemoteDataStore().getLatestMovies(pageNum).toFlowable().doOnNext {
                    factory.retrieveCasheDataStore().saveLatestMovies(it)
                }).firstElement().toFlowable()
    }

    override fun getPopularMovies(pageNum: Int): Flowable<MoviesEntity> {
        return Flowable.concatArrayDelayError(factory.retrieveCasheDataStore().getPopularMovies(pageNum).toFlowable(),
                factory.retrieveRemoteDataStore().getPopularMovies(pageNum).toFlowable().doOnNext {
                    factory.retrieveCasheDataStore().savePopularMovies(it)
                }).firstElement().toFlowable()

    }

    override fun getTopRatedMovies(pageNum: Int): Flowable<MoviesEntity> {
        return Flowable.concatArrayDelayError(factory.retrieveCasheDataStore().getTopRatedMovies(pageNum).toFlowable(),
                factory.retrieveRemoteDataStore().getTopRatedMovies(pageNum).toFlowable().doOnNext {
                    factory.retrieveCasheDataStore().saveTopRatedMovies(it)
                }).firstElement().toFlowable()
    }


}