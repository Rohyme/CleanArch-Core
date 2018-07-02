package com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic

import com.tripl3dev.domain.Entity.TvShowsEntity
import io.reactivex.Single

interface TvShowsDataStore {
    fun getTvShows(pageNum: Int): Single<TvShowsEntity>
}