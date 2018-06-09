package com.tripl3dev.dataStore.login

import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginCacheI
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRemoteI
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepositoryI
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class LoginDataStoreFactory @Inject constructor(loginCache: LoginCacheImp, loginRemote: LoginRemoteImp)
    : BaseDataStoreFactory<LoginRepositoryI, LoginCacheI, LoginRemoteI>(loginCache, loginRemote) {
}