package com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic

import com.tripl3dev.domain.Entity.TvShowsEntity
import io.reactivex.Flowable

interface TvShowsRepositoryI {
    fun getTvShows(pageNum: Int): Flowable<TvShowsEntity>
}