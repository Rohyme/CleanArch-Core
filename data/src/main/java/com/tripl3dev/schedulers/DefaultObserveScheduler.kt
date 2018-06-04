package com.tripl3dev.schedulers

import com.tripl3dev.domain.executer.ObserveOnScheduler
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DefaultObserveScheduler @Inject constructor() : ObserveOnScheduler {
    override fun getObserveOnScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}