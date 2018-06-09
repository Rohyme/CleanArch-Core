package com.tripl3dev.domain.managers

sealed class Stateview {

    data class Success<out T>(val data: T) : Stateview()
    data class Failure(var error: Throwable) : Stateview()
    data class ServiceError(var errorCode: Int) : Stateview()
    object Loading : Stateview()
    object HasNoData : Stateview()


}