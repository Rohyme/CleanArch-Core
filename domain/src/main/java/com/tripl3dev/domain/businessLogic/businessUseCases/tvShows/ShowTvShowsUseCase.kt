package com.tripl3dev.domain.businessLogic.businessUseCases.tvShows

import com.tripl3dev.domain.Entity.TvShowsEntity
import com.tripl3dev.domain.businessLogic.dataLogic.tvShowsLogic.TvShowsRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class ShowTvShowsUseCase @Inject constructor(val repository: TvShowsRepositoryI,
                                             observer: ObserveOnScheduler,
                                             subscribe: SubscribtionOnScheduler) : FlowableUseCase<TvShowsEntity, Int>(observer, subscribe) {
    override fun buildUseCaseObservable(params: Int?): Flowable<TvShowsEntity> {
        return repository.getTvShows(params!!)
    }
}