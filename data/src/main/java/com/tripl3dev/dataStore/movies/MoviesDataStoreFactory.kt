package com.tripl3dev.dataStore.movies

import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesCacheI
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesRemoteI
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesRepositoryI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class MoviesDataStoreFactory @Inject constructor(cache: MoviesCacheImp,
                                                 remote: MoviesRemoteImp) : BaseDataStoreFactory<MoviesRepositoryI, MoviesCacheI, MoviesRemoteI>(cache, remote)