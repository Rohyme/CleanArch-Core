package com.tripl3dev.dataStore.movies

import android.util.Log
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesCacheI
import io.reactivex.Single
import javax.inject.Inject

class MoviesCacheImp @Inject constructor(val moviesDb: MoviesDao) : MoviesCacheI {
    override fun getLatestMovies(pageNum: Int): Single<MoviesEntity> {
        return moviesDb.getLatestMovies().onErrorResumeNext {
            Log.e("Cache", "latest is Empty and error is ${it.message}")
            return@onErrorResumeNext Single.error(it)
        }
                .doOnSuccess {
                    Log.e("Cache", "Latest from cache ${it.toString()}")
                }
    }

    override fun savePopularMovies(moviesEntity: MoviesEntity) {
        moviesEntity.MoviesType = MoviesRepositoryImp.POPULAR_MOVIES
        Log.e("Cache", "Saving Popular $moviesEntity")
        moviesDb.insertMoviesList(moviesEntity)
    }

    override fun saveLatestMovies(moviesEntity: MoviesEntity) {
        moviesEntity.MoviesType = MoviesRepositoryImp.LATEST_MOVIES
        Log.e("Cache", "Saving Latest $moviesEntity")

        moviesDb.insertMoviesList(moviesEntity)
    }

    override fun saveTopRatedMovies(moviesEntity: MoviesEntity) {
        moviesEntity.MoviesType = MoviesRepositoryImp.TOP_RATED_MOVIES
        Log.e("Cache", "Saving TopRated $moviesEntity")
        moviesDb.insertMoviesList(moviesEntity)
    }

    override fun getPopularMovies(pageNum: Int): Single<MoviesEntity> {
        return moviesDb.getPopularMovies().onErrorResumeNext {
            Log.e("Cache", "popular is Empty and error is ${it.message}")
            return@onErrorResumeNext Single.error(it)
        }
                .doOnSuccess {
                    Log.e("Cache", "Popular from cache ${it.toString()}")
                }
    }

    override fun getTopRatedMovies(pageNum: Int): Single<MoviesEntity> {
        return moviesDb.getTopRatedMovies()
                .onErrorResumeNext {
                    Log.e("Cache", "topRated is Empty and error is ${it.message}")
                    return@onErrorResumeNext Single.error(it)
                }
                .doOnSuccess {
                    Log.e("Cache", "topRated from cache ${it.toString()}")
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