package com.tripl3dev.domain.businessLogic.dataLogic.authenticationLogic

import com.tripl3dev.domain.Entity.UserEntity
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI

interface AuthenticationCasheI  :AuthenticationBaseRepository,BaseCasheI{
    fun saveUser(user : UserEntity)
    fun deleteUser()

}