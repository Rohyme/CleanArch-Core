package com.tripl3dev.domain.interactor

import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import io.reactivex.Completable
import io.reactivex.disposables.Disposables

abstract class CompletableUseCase<in params> constructor(
        private val observeOnScheduler: ObserveOnScheduler,
        private val subscribeOnScheduler: SubscribtionOnScheduler) {

    val subscribtion = Disposables.empty()

    protected abstract fun buildUseCaseObservable(params: params): Completable

    fun execute(params: params):Completable{
        return this.buildUseCaseObservable(params)
                .observeOn(observeOnScheduler.getObserveOnScheduler())
                .subscribeOn(subscribeOnScheduler.getSubscribtionOnScheduler())
    }

    fun  unSubscribe(){
        if (!subscribtion.isDisposed){
            subscribtion.dispose()
        }
    }



}