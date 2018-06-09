package com.tripl3dev.domain.businessLogic.dataLogic.loginLogic

import com.tripl3dev.domain.Entity.GuestEntity
import io.reactivex.Single

interface LoginRepositoryI {
    fun loginUser(): Single<GuestEntity>
    fun checkUserLoggedIn(): Single<Boolean>? {
        return null
    }
}