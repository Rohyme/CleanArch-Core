package com.tripl3dev.dataStore.movies

import android.util.Log
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesCacheI
import io.reactivex.Single
import javax.inject.Inject

class MoviesCacheImp @Inject constructor(val moviesDb: MoviesDao) : MoviesCacheI {
    override fun getLatestMovies(pageNum: Int): Single<MoviesEntity> {
        Log.e("Cache", "getLatestMoviesFromCache")
        return moviesDb.getLatestMovies(pageNum).onErrorResumeNext {
            return@onErrorResumeNext Single.error(it)
        }
                .doOnSuccess {
                    Log.e("Cache", "getTopRatedFromCache")
                }
    }

    override fun savePopularMovies(moviesEntity: MoviesEntity) {
        moviesEntity.MoviesType = MoviesRepositoryImp.POPULAR_MOVIES
        moviesDb.insertMoviesList(moviesEntity)
    }

    override fun saveLatestMovies(moviesEntity: MoviesEntity) {
        moviesEntity.MoviesType = MoviesRepositoryImp.LATEST_MOVIES
        moviesDb.insertMoviesList(moviesEntity)
    }

    override fun saveTopRatedMovies(moviesEntity: MoviesEntity) {
        moviesEntity.MoviesType = MoviesRepositoryImp.TOP_RATED_MOVIES
        moviesDb.insertMoviesList(moviesEntity)
    }

    override fun getPopularMovies(pageNum: Int): Single<MoviesEntity> {
        Log.e("Cache", "getPopularMoviesFromCache")
        return moviesDb.getPopularMovies(pageNum).onErrorResumeNext {
            return@onErrorResumeNext Single.error(it)
        }
                .doOnSuccess {
                    Log.e("Cache", "getTopRatedFromCache")
                }
    }

    override fun getTopRatedMovies(pageNum: Int): Single<MoviesEntity> {
        return moviesDb.getTopRatedMovies(pageNum)
                .doOnSuccess {
                    Log.e("Cache", "getTopRatedFromCache")
                }
                .onErrorResumeNext {
                    return@onErrorResumeNext Single.error(it)
                }
    }

    fun isMoviedCached(pageNum: Int, type: Int): Boolean {
        return moviesDb.checkMoviesExist(pageNum, type) > 0
    }

    override fun isCached(): Boolean {
        return true
    }

    override fun setLastCacheTime(lastCache: Long) {
    }

    override fun isExpired(): Boolean {
        return false
    }


}