package com.tripl3dev.dataStore.people

import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic.PeopleRepositoryI
import io.reactivex.Flowable
import javax.inject.Inject

class PeopleRepositoryImp @Inject constructor(val factory: PeopleDataStoreFactory) : PeopleRepositoryI {
    override fun getActors(pageNum: Int): Flowable<ActorEntity> {
        return Flowable.concatArrayDelayError(factory.retrieveCasheDataStore().getActors(pageNum).toFlowable(),
                factory.retrieveRemoteDataStore().getActors(pageNum).toFlowable().doOnNext {
                    factory.retrieveCasheDataStore().saveActors(it)
                }).firstElement().toFlowable()
    }
}