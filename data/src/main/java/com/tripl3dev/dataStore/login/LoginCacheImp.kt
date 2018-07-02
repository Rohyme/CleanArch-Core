package com.tripl3dev.dataStore.login

import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginCacheI
import com.tripl3dev.domain.managers.getDateInMilliSeconds
import com.tripl3dev.utils.SharedPreferenceUtil
import io.reactivex.Single
import javax.inject.Inject

class LoginCacheImp @Inject constructor(val sharedPreferences: SharedPreferenceUtil) : LoginCacheI {
    override fun saveGuestInfo(guest: GuestEntity) {
        sharedPreferences.putGuestData(guest)
    }

    override fun loginUser(): Single<GuestEntity> {
        return Single.just(sharedPreferences.getGuestData())
    }

    override fun isCached(): Boolean {
        return sharedPreferences.getGuestData() != null && ((sharedPreferences.getGuestData()?.expiredAt?.getDateInMilliSeconds())!! - System.currentTimeMillis()) > 0
    }

    override fun setLastCacheTime(lastCache: Long) {
    }

    override fun isExpired(): Boolean {
        return true
    }

    override fun checkUserLoggedIn(): Single<Boolean>? {
        return if (isCached())
            Single.just(true)
        else
            Single.just(false)
    }

}