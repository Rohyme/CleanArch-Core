package com.tripl3dev.domain.businessLogic.dataLogic.loginLogic

import com.tripl3dev.domain.Entity.GuestEntity
import io.reactivex.Single

interface LoginRepository {
    fun loginUser(): Single<GuestEntity>
}