package com.tripl3dev.domain.repository.businessData.authenticationLogic

import com.tripl3dev.domain.Entity.UserEntity
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI

interface AuthenticationCasheI  :AuthenticationBaseStore,BaseCasheI{
    fun saveUser(user : UserEntity)
    fun deleteUser()

}