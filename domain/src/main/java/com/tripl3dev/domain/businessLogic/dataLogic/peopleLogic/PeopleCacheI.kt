package com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic

import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI

interface PeopleCacheI : BaseCasheI, PeopleDataStore {
    fun saveActors(actor: ActorEntity)
}