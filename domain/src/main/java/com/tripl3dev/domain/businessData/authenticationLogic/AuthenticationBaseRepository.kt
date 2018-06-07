package com.tripl3dev.domain.businessData.authenticationLogic

import com.tripl3dev.domain.Entity.UserEntity
import io.reactivex.Single


interface AuthenticationBaseStore  {
    fun getUser() :Single<UserEntity>?{return  null}
}