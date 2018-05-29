package com.tripl3dev.domain.executer


import io.reactivex.Scheduler

interface SubscribtionOnScheduler{
    fun getSubscribtionOnScheduler():Scheduler
}