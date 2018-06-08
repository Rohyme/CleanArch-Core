package com.tripl3dev.domain.interactor

import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException

class CustomSingleObserver<T>(val singleObserverImp: SingleObserverCB<T>) : DisposableSingleObserver<T>() {
    override fun onSuccess(t: T) {
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

    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                singleObserverImp.onHttpError(e.code(), e.message())
            }
            else -> {
                singleObserverImp.onThrowableError(e)
            }
        }
        singleObserverImp.onError(e)
    }

    override fun onStart() {
        super.onStart()
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

}