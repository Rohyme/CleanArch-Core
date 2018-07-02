package com.tripl3dev.dataStore.people

import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic.PeopleRemoteI
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.domain.service.ApiService
import io.reactivex.Single
import javax.inject.Inject

class PeopleRemoteImp @Inject constructor(val service: ApiService) : PeopleRemoteI {
    override fun getActors(pageNum: Int): Single<ActorEntity> {
        return service.getPopularPeople(Constants.MOVIES_API_TOKEN, pageNum)
    }
}