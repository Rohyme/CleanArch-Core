package com.tripl3dev.domain.interactor

import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver


abstract class SingleUseCase<T, in params> constructor(
        private val oberserveOnScheduler: ObserveOnScheduler,
        private val subscribeOnScheduler: SubscribtionOnScheduler
) {

    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: params? = null): Single<T>


    open fun execute(singleObserver: DisposableSingleObserver<T>, params: params? = null) {

        val single = this.buildUseCaseObservable(params)
                .subscribeOn(subscribeOnScheduler.getSubscribtionOnScheduler())
                .observeOn(oberserveOnScheduler.getObserveOnScheduler()) as Single<T>
        addDisposable(single.subscribeWith(singleObserver))
    }


    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}