package com.tripl3dev.dataStore.tvShows

import com.tripl3dev.domain.Entity.TvShowsEntity
import com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic.TvShowsRemoteI
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.domain.service.ApiService
import io.reactivex.Single
import javax.inject.Inject

class TvShowsRemoteImp @Inject constructor(val service: ApiService) : TvShowsRemoteI {
    override fun getTvShows(pageNum: Int): Single<TvShowsEntity> {
        return service.getPopularTvShows(Constants.MOVIES_API_TOKEN, pageNum)
    }
}