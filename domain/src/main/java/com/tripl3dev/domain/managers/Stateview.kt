package com.tripl3dev.domain.managers

sealed class Stateview {

    data class Success(var data: Any) : Stateview()
    data class Failure(var error: Any) : Stateview()
    data class Loading(var isLoading: Boolean) : Stateview()
    object HasNoData : Stateview()

    companion object {
        fun onSuccess(data: Any) = Success(data)
        fun onFailure(error: Any) = Failure(error)
        fun isLoading(loading: Boolean) = Loading(loading)
        fun hasNoData() = HasNoData
    }
}