package com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic

import com.tripl3dev.domain.Entity.TvShowsEntity
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI

interface TvShowsCacheI : BaseCasheI, TvShowsDataStore {
    fun saveTvShows(tvShowsEntity: TvShowsEntity)
}