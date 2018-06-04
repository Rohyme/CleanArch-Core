package com.tripl3dev.dataStore.authentication

import com.tripl3dev.domain.Entity.UserEntity
import com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic.AuthenticationRemoteI
import com.tripl3dev.domain.service.ApiService
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRemoteImp @Inject constructor(val service:ApiService) : AuthenticationRemoteI {
    override fun getUser(): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}