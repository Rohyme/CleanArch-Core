package com.tripl3dev.dataStore.movies

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

    override fun getLatestMovies(): Single<MoviesEntity> {
        return Single.concat(factory.retrieveCasheDataStore().getLatestMovies(), factory.retrieveRemoteDataStore().getLatestMovies()).first(MoviesEntity())
    }

    override fun getPopularMovies(pageNum: Int): Single<MoviesEntity> {
        return if (isCached(pageNum, LATEST_MOVIES)) {
            factory.retrieveCasheDataStore().getPopularMovies(pageNum)
        } else {
            factory.retrieveRemoteDataStore().getPopularMovies(pageNum)
                    .doOnSuccess {
                        factory.retrieveCasheDataStore().savePopularMovies(it)
                    }
        }
    }

    override fun getTopRatedMovies(pageNum: Int): Single<MoviesEntity> {
        return if (isCached(pageNum, TOP_RATED_MOVIES)) {
            factory.retrieveCasheDataStore().getTopRatedMovies(pageNum)
        } else {
            factory.retrieveRemoteDataStore().getTopRatedMovies(pageNum)
                    .doOnSuccess {
                        factory.retrieveCasheDataStore().saveTopRatedMovies(it)
                    }
        }
    }

    fun isCached(pageNum: Int, moviesType: Int): Boolean {
        return (factory.retrieveCasheDataStore() as MoviesCacheImp).isMoviedCached(pageNum, moviesType)
    }

}