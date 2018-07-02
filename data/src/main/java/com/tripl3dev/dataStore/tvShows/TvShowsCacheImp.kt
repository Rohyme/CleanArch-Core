package com.tripl3dev.dataStore.tvShows

import com.tripl3dev.domain.Entity.TvShowsEntity
import com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic.TvShowsCacheI
import io.reactivex.Single
import javax.inject.Inject

class TvShowsCacheImp @Inject constructor(val dao: TvShowsDao) : TvShowsCacheI {
    override fun saveTvShows(tvShowsEntity: TvShowsEntity) {
        dao.insertTvShows(tvShowsEntity)
    }

    override fun getTvShows(pageNum: Int): Single<TvShowsEntity> {
        return dao.getTvShows(pageNum)
    }

    override fun isCached(): Boolean {
        return true
    }

    override fun setLastCacheTime(lastCache: Long) {
    }

    override fun isExpired(): Boolean {
        return false
    }

}