package com.tripl3dev.dataStore.login

import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseDataStoreFactory
import javax.inject.Inject

class LoginDataStoreFactory @Inject constructor(loginCache: LoginCacheImp, LoginRemote: LoginRemoteImp)
    : BaseDataStoreFactory<LoginRepositoryImp, LoginCacheImp, LoginRemoteImp>(loginCache, LoginRemote) {
}