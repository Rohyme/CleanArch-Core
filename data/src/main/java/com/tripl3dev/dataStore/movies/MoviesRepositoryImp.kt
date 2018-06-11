package com.tripl3dev.dataStore.movies

import android.arch.persistence.room.EmptyResultSetException
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesRepositoryI
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(val factory: MoviesDataStoreFactory) : MoviesRepositoryI {
    companion object {
        const val POPULAR_MOVIES = -10
        const val TOP_RATED_MOVIES = -12
        const val LATEST_MOVIES = -11
    }

    override fun getLatestMovies(pageNum: Int): Single<MoviesEntity> {
        return factory.retrieveCasheDataStore().getLatestMovies(pageNum)
                .onErrorResumeNext {
                    if (it is EmptyResultSetException) {
                        return@onErrorResumeNext factory.retrieveRemoteDataStore().getLatestMovies(pageNum).doOnSuccess {
                            factory.retrieveCasheDataStore().saveLatestMovies(it)
                        }
                    }
                    return@onErrorResumeNext Single.error(it)
                }
    }

    override fun getPopularMovies(pageNum: Int): Single<MoviesEntity> {
        return factory.retrieveCasheDataStore().getPopularMovies(pageNum)
                .onErrorResumeNext {
                    if (it is EmptyResultSetException) {
                        return@onErrorResumeNext factory.retrieveRemoteDataStore().getPopularMovies(pageNum).doOnSuccess {
                            factory.retrieveCasheDataStore().savePopularMovies(it)
                        }
                    }
                    return@onErrorResumeNext Single.error(it)
                }


    }

    override fun getTopRatedMovies(pageNum: Int): Single<MoviesEntity> {
        return factory.retrieveCasheDataStore().getTopRatedMovies(pageNum)
                .onErrorResumeNext {
                    if (it is EmptyResultSetException) {
                        return@onErrorResumeNext factory.retrieveRemoteDataStore().getTopRatedMovies(pageNum).doOnSuccess {
                            factory.retrieveCasheDataStore().saveTopRatedMovies(it)
                        }
                    }
                    return@onErrorResumeNext Single.error(it)
                }
    }

    fun isCached(pageNum: Int, moviesType: Int): Boolean {
        return (factory.retrieveCasheDataStore() as MoviesCacheImp).isMoviedCached(pageNum, moviesType)
    }

}