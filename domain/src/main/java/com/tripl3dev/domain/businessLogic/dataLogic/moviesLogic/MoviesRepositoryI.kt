package com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic

import com.tripl3dev.domain.Entity.MoviesEntity
import io.reactivex.Single

interface MoviesRepositoryI {
    fun getLatestMovies(pageNum: Int): Single<MoviesEntity>
    fun getPopularMovies(pageNum: Int): Single<MoviesEntity>
    fun getTopRatedMovies(pageNum: Int): Single<MoviesEntity>
}