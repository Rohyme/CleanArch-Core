package com.tripl3dev.dataStore.authentication

import com.tripl3dev.domain.Entity.UserEntity
import com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic.AuthenticationRepositoryI
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(val authFactory: AuthenticationDataStoreFactory) : AuthenticationRepositoryI {
    override fun getUser(): Single<UserEntity> {
        return authFactory.retrieveDataStore().getUser()
                .doOnSuccess { saveUser(it) }
    }

    fun saveUser(user: UserEntity) {
        authFactory.retrieveCasheDataStore().saveUser(user)
    }

}