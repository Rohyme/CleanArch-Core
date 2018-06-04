package com.tripl3dev.dataStore.authentication


import com.tripl3dev.domain.Entity.UserEntity
import com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic.AuthenticationCasheI
import javax.inject.Inject

class AuthenticationCasheIImp @Inject constructor() : AuthenticationCasheI{
    override fun getUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUser(user: UserEntity) {

    }

    override fun deleteUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}