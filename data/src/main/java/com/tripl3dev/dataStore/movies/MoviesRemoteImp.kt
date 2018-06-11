package com.tripl3dev.dataStore.movies

import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesRemoteI
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.domain.service.ApiService
import io.reactivex.Single
import javax.inject.Inject

class MoviesRemoteImp @Inject constructor(val service: ApiService) : MoviesRemoteI {
    override fun getLatestMovies(): Single<MoviesEntity> {
        return service.getLatestMovies(Constants.MOVIES_API_TOKEN)
    }

    override fun getPopularMovies(pageNum: Int): Single<MoviesEntity> {
        return service.getPopularMovies(Constants.MOVIES_API_TOKEN, pageNum)
    }

    override fun getTopRatedMovies(pageNum: Int): Single<MoviesEntity> {
        return service.getTopRatedMovies(Constants.MOVIES_API_TOKEN, pageNum)
    }
}