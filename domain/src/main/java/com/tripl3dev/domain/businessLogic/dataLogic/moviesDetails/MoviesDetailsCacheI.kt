package com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails

import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI

interface MoviesDetailsCacheI : BaseCasheI, MoviesDetailsDataStoreI {
    fun saveMovieDetails(movie: MovieDetails)

}