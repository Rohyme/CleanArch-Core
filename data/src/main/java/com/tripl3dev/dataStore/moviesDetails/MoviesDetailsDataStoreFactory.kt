package com.tripl3dev.dataStore.moviesDetails

import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsCacheI
import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsDataStoreI
import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsRemoteI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class MoviesDetailsDataStoreFactory @Inject constructor(cache: MoviesDetailsCacheImp, remote: MoviesDetailsRemoteImp) :
        BaseDataStoreFactory<MoviesDetailsDataStoreI, MoviesDetailsCacheI, MoviesDetailsRemoteI>(cache, remote) {
}