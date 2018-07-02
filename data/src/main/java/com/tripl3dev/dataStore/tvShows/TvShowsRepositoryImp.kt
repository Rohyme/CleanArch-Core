package com.tripl3dev.dataStore.tvShows

import com.tripl3dev.domain.Entity.TvShowsEntity
import com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic.TvShowsRepositoryI
import io.reactivex.Flowable
import javax.inject.Inject

class TvShowsRepositoryImp @Inject constructor(val factory: TvShowsDataStoreFactory) : TvShowsRepositoryI {
    override fun getTvShows(pageNum: Int): Flowable<TvShowsEntity> {
        return Flowable.concatArrayDelayError(factory.retrieveCasheDataStore().getTvShows(pageNum).toFlowable(),
                factory.retrieveRemoteDataStore().getTvShows(pageNum).toFlowable().doOnNext {
                    factory.retrieveCasheDataStore().saveTvShows(it)
                }).firstElement().toFlowable()

    }

}