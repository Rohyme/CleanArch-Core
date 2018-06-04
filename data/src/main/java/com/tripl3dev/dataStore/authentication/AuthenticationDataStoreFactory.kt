package com.tripl3dev.dataStore.authentication

import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic.AuthenticationRepositoryI
import com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic.AuthenticationCasheI
import com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic.AuthenticationRemoteI
import javax.inject.Inject

class AuthenticationDataStoreFactory @Inject constructor(
        cashe: AuthenticationCasheImp,
        remote: AuthenticationRemoteImp
)  : BaseDataStoreFactory<AuthenticationRepositoryI,AuthenticationCasheI,AuthenticationRemoteI>(cashe,remote) {



}