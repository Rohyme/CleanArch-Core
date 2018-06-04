package com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic

import com.tripl3dev.domain.Entity.UserEntity
import io.reactivex.Single


interface AuthenticationBaseRepository  {
    fun getUser() :Single<UserEntity>?{return  null}
}