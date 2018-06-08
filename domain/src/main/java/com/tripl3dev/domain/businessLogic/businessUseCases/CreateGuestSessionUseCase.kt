package com.tripl3dev.domain.businessLogic.businessUseCases

import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepository
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CreateGuestSessionUseCase @Inject constructor(val guestRepository: LoginRepository,
                                                    observeOnScheduler: ObserveOnScheduler,
                                                    subscribeScheduler: SubscribtionOnScheduler) : SingleUseCase<GuestEntity, Void>(observeOnScheduler, subscribeScheduler) {
    override fun buildUseCaseObservable(params: Void?): Single<GuestEntity> {
        return guestRepository.loginUser()
    }

}