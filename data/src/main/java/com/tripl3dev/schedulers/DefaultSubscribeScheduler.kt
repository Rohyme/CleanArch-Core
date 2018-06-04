package com.tripl3dev.schedulers

import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultSubscribeScheduler @Inject constructor() : SubscribtionOnScheduler {
    override fun getSubscribtionOnScheduler(): Scheduler {
        return Schedulers.io()
    }

}