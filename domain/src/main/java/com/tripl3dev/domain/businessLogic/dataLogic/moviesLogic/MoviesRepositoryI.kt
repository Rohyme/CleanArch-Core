package com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic

import com.tripl3dev.domain.Entity.MoviesEntity
import io.reactivex.Flowable

interface MoviesRepositoryI {
    fun getLatestMovies(pageNum: Int): Flowable<MoviesEntity>
    fun getPopularMovies(pageNum: Int): Flowable<MoviesEntity>
    fun getTopRatedMovies(pageNum: Int): Flowable<MoviesEntity>
}