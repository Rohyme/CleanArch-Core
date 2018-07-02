package com.tripl3dev.domain.businessLogic.businessUseCases.people

import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic.PeopleRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class ShowPeopleUseCase @Inject constructor(val repository: PeopleRepositoryI,
                                            observer: ObserveOnScheduler,
                                            subscriber: SubscribtionOnScheduler) : FlowableUseCase<ActorEntity, Int>(observer, subscriber) {
    override fun buildUseCaseObservable(params: Int?): Flowable<ActorEntity> {
        return repository.getActors(params!!)
    }
}