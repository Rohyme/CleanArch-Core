package com.tripl3dev.dataStore.login

import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepositoryI
import io.reactivex.Single
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(val factory: LoginDataStoreFactory) : LoginRepositoryI {
    override fun checkUserLoggedIn(): Single<Boolean>? {
        return factory.retrieveCasheDataStore().checkUserLoggedIn()
    }

    override fun loginUser(): Single<GuestEntity> {
        return factory.retrieveDataStore().loginUser().doOnSuccess {
            if (factory.fromRemote)
                factory.retrieveCasheDataStore().saveGuestInfo(it)
        }
    }
}