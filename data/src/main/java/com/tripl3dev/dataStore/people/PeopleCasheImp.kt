package com.tripl3dev.dataStore.people

import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic.PeopleCacheI
import io.reactivex.Single
import javax.inject.Inject

class PeopleCasheImp @Inject constructor(val dao: PeopleDao) : PeopleCacheI {
    override fun saveActors(actor: ActorEntity) {
        dao.saveActors(actor)
    }

    override fun isCached(): Boolean {
        return true
    }

    override fun setLastCacheTime(lastCache: Long) {
    }

    override fun isExpired(): Boolean {
        return false
    }

    override fun getActors(pageNum: Int): Single<ActorEntity> {
        return dao.getActors(pageNum)
    }
}