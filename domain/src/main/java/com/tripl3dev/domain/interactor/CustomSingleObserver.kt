package com.tripl3dev.domain.interactor

import retrofit2.HttpException

class CustomSingleObserver<T>(private val singleObserverImp: SingleObserverCB<T>) {
    fun onSuccess(t: T) {
        if (t is AbstractList<*>) {
            if (t.isEmpty()) {
                singleObserverImp.onEmptyList()
            } else {
                singleObserverImp.onSuccess(t)
            }
        } else {
            singleObserverImp.onSuccess(t)
        }
    }

    fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                singleObserverImp.onHttpError(e.code(), e.message())

            }
            else -> {
                singleObserverImp.onThrowableError(e)
            }

        }
        if (!singleObserverImp.hasPreviousData()) {
            singleObserverImp.onHandledError(e)
        }
        singleObserverImp.onError(e)
    }


    fun onSubscribe() {
        singleObserverImp.onSubscribe()
    }
}

interface SingleObserverCB<T> {
    fun onSubscribe() {}
    fun onSuccess(t: T)
    fun onEmptyList() {}
    fun onHttpError(errorCode: Int, message: String) {}
    fun onThrowableError(e: Throwable) {}
    fun onError(e: Throwable) {}
    fun hasPreviousData(): Boolean {
        return false
    }

    fun onHandledError(e: Throwable) {

    }

}