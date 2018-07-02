package com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic

import com.tripl3dev.domain.Entity.ActorEntity
import io.reactivex.Single

interface PeopleDataStore {
    fun getActors(pageNum: Int): Single<ActorEntity>
}