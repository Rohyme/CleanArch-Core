package com.tripl3dev.dataStore.people

import com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic.PeopleCacheI
import com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic.PeopleDataStore
import com.tripl3dev.domain.businessLogic.dataLogic.peopleLogic.PeopleRemoteI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class PeopleDataStoreFactory @Inject constructor(cache: PeopleCasheImp, remote: PeopleRemoteImp) :
        BaseDataStoreFactory<PeopleDataStore, PeopleCacheI, PeopleRemoteI>(cache, remote) {
}