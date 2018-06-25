package com.tripl3dev.domain.interactor

import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import io.reactivex.BackpressureStrategy
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


abstract class SingleUseCase<T, in params> constructor(
        private val oberserveOnScheduler: ObserveOnScheduler,
        private val subscribeOnScheduler: SubscribtionOnScheduler
) {

    private var publisher: PublishSubject<Long> = PublishSubject.create()

    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: params? = null): Single<T>


    open fun execute(singleObserver: CustomSingleObserver<T>, params: params? = null) {

        val single = this.buildUseCaseObservable(params)

                .doOnError {
                    singleObserver.onError(it)
                }
                .doOnSubscribe {
                    singleObserver.onSubscribe()
                }.retryWhen {
                    return@retryWhen publisher.toFlowable(BackpressureStrategy.LATEST)
                }
                .subscribeOn(subscribeOnScheduler.getSubscribtionOnScheduler())
                .observeOn(oberserveOnScheduler.getObserveOnScheduler()) as Single<T>
        addDisposable(single.subscribe({ singleObserver.onSuccess(it) }, { singleObserver.onError(it) }))
    }

    fun retry() {
        publisher.onNext(System.currentTimeMillis())
    }
    fun unSubscribe() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}