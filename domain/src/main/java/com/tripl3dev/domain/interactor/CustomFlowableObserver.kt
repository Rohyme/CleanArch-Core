package com.tripl3dev.domain.interactor

import retrofit2.HttpException

class CustomFlowableObserver<T>(private val singleObserverImp: SingleObserverCB<T>) {
    fun onComplete() {


    }

    fun onNext(t: T) {
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

    fun onStart() {
        singleObserverImp.onSubscribe()
    }

    fun onSubscribe() {
        singleObserverImp.onSubscribe()
    }
}

