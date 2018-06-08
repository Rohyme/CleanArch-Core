package com.tripl3dev.dataStore.login

import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRemoteI
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.domain.service.ApiService
import io.reactivex.Single
import javax.inject.Inject

class LoginRemoteImp @Inject constructor(val retrofit: ApiService) : LoginRemoteI {
    override fun loginUser(): Single<GuestEntity> {
        return retrofit.getGuestToken(Constants.MOVIES_API_TOKEN)
    }
}