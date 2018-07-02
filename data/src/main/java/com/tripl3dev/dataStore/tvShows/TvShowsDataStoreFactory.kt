package com.tripl3dev.dataStore.tvShows

import com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic.TvShowsCacheI
import com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic.TvShowsDataStore
import com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic.TvShowsRemoteI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class TvShowsDataStoreFactory @Inject constructor(cache: TvShowsCacheImp, remote: TvShowsRemoteImp) : BaseDataStoreFactory<TvShowsDataStore, TvShowsCacheI, TvShowsRemoteI>(cache, remote)