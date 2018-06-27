package com.tripl3dev.dataStore.movies

import android.util.Log
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesCacheI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.CacheManager
import io.reactivex.Single
import javax.inject.Inject

class MoviesCacheImp @Inject constructor(val moviesDb: MoviesDao) : MoviesCacheI {

    val lastCachedMap: HashMap<Int, Long> = HashMap()

    private val latestCacheManager = CacheManager()
    private val topRatedCacheManager = CacheManager()
    private val popularCacheManager = CacheManager()
    override fun getLatestMovies(pageNum: Int): Single<MoviesEntity> {
        return moviesDb.getLatestMovies(pageNum).onErrorResumeNext {
            return@onErrorResumeNext Single.error(it)
        }
    }

    override fun savePopularMovies(moviesEntity: MoviesEntity) {
        moviesEntity.setMoviesTypeAndId(MoviesRepositoryImp.POPULAR_MOVIES)
        moviesDb.insertMoviesList(moviesEntity)
        popularCacheManager.setLastWriteTime()

    }

    override fun saveLatestMovies(moviesEntity: MoviesEntity) {
        moviesEntity.setMoviesTypeAndId(MoviesRepositoryImp.LATEST_MOVIES)
        moviesDb.insertMoviesList(moviesEntity)
        latestCacheManager.setLastWriteTime()

    }

    override fun saveTopRatedMovies(moviesEntity: MoviesEntity) {
        moviesEntity.setMoviesTypeAndId(MoviesRepositoryImp.TOP_RATED_MOVIES)
        moviesDb.insertMoviesList(moviesEntity)
        topRatedCacheManager.setLastWriteTime()

    }

    override fun getPopularMovies(pageNum: Int): Single<MoviesEntity> {
        return moviesDb.getPopularMovies(pageNum).onErrorResumeNext {
            return@onErrorResumeNext Single.error(it)
        }.doOnSuccess {
            Log.e("POPULAR_CACHE", it.uniqueId)
            Log.e("POPULAR_CACHE", it.toString())
        }
    }

    override fun getTopRatedMovies(pageNum: Int): Single<MoviesEntity> {
        return moviesDb.getTopRatedMovies(pageNum)
                .onErrorResumeNext {
                    return@onErrorResumeNext Single.error(it)
                }
    }


    override fun isCached(): Boolean {
        return true
    }

    override fun setLastCacheTime(lastCache: Long) {
    }

    override fun isExpired(): Boolean {
        return false
    }

    override fun isExpired(type: Int): Boolean {

        return when (type) {
            MoviesRepositoryImp.TOP_RATED_MOVIES -> {
                topRatedCacheManager.isExpired()
            }
            MoviesRepositoryImp.LATEST_MOVIES -> {
                latestCacheManager.isExpired()
            }

            MoviesRepositoryImp.POPULAR_MOVIES -> {
                popularCacheManager.isExpired()
            }
            else -> {
                false
            }
        }
    }


}