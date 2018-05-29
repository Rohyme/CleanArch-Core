package com.tripl3dev.domain.executer

import io.reactivex.Scheduler

interface ObserveOnScheduler{
    fun getObserveOnScheduler(): Scheduler
}