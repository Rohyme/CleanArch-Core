package com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic

import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI

interface MoviesCacheI : BaseCasheI, MoviesDataStore {

    fun isExpired(type: Int): Boolean
    fun savePopularMovies(moviesEntity: MoviesEntity)
    fun saveLatestMovies(moviesEntity: MoviesEntity)
    fun saveTopRatedMovies(moviesEntity: MoviesEntity)
}