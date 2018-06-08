package com.tripl3dev.dataStore.login

import com.tripl3dev.dataStore.SharedPreferenceUtil
import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginCacheI
import com.tripl3dev.domain.managers.getDateInMilliSeconds
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
        return sharedPreferences.getGuestData() != null && (System.currentTimeMillis() - (sharedPreferences.getGuestData()?.expiredAt?.getDateInMilliSeconds())!!) > 0
    }

    override fun setLastCacheTime(lastCache: Long) {
    }

    override fun isExpired(): Boolean {
        return true
    }
}