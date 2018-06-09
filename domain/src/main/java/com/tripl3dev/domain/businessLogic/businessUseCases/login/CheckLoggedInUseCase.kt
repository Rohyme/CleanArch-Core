package com.tripl3dev.domain.businessLogic.businessUseCases.login

import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CheckLoggedInUseCase @Inject constructor(val guestRepositoryI: LoginRepositoryI,
                                               observeOnScheduler: ObserveOnScheduler,
                                               subscribeScheduler: SubscribtionOnScheduler) : SingleUseCase<Boolean, Void>(observeOnScheduler, subscribeScheduler) {
    override fun buildUseCaseObservable(params: Void?): Single<Boolean> {
        return guestRepositoryI.checkUserLoggedIn()!!
    }

}