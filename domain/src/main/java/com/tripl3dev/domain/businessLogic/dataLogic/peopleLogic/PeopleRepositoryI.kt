package com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic

import com.tripl3dev.domain.Entity.ActorEntity
import io.reactivex.Flowable

interface PeopleRepositoryI {
    fun getActors(pageNum: Int): Flowable<ActorEntity>
}