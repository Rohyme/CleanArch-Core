package com.tripl3dev.dataStore.login

import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(val factory: LoginDataStoreFactory) : LoginRepository {
    override fun loginUser(): Single<GuestEntity> {
        return factory.retrieveDataStore().loginUser().doOnSuccess {
            if (factory.fromRemote)
                factory.retrieveCasheDataStore().saveGuestInfo(it)
        }
    }
}