package com.tripl3dev.domain.interactor

import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


abstract class FlowableUseCase<T, in params> constructor(
        private val oberserveOnScheduler: ObserveOnScheduler,
        private val subscribeOnScheduler: SubscribtionOnScheduler
) {


    private var publisher: PublishSubject<Long> = PublishSubject.create()
//    private   var publisher :PublishRelay<Long> = PublishRelay.create()


    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: params? = null): Flowable<T>


    open fun execute(singleObserver: CustomFlowableObserver<T>, params: params? = null) {

        val flowable = this.buildUseCaseObservable(params)
                .subscribeOn(subscribeOnScheduler.getSubscribtionOnScheduler())
                .observeOn(oberserveOnScheduler.getObserveOnScheduler())

                .doOnSubscribe {
                    singleObserver.onSubscribe()
                }
                .doOnError {
                    singleObserver.onError(it)
                }
                .retryWhen {
                    return@retryWhen publisher.toFlowable(BackpressureStrategy.LATEST)
                }


        addDisposable(flowable.subscribe({ singleObserver.onNext(it) }, { singleObserver.onError(it) }))
    }

    open fun retry() {
        publisher.onNext(System.currentTimeMillis())
    }


    fun unSubscribe() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}