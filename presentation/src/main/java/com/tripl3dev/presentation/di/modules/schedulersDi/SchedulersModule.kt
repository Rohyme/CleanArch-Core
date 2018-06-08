package com.tripl3dev.presentation.di.modules.schedulersDi

import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.schedulers.DefaultObserveScheduler
import com.tripl3dev.schedulers.DefaultSubscribeScheduler
import dagger.Module
import dagger.Provides

@Module
class SchedulersModule {

    @Provides
    fun provideObserveOnScheduler(scheduler: DefaultObserveScheduler): ObserveOnScheduler {
        return scheduler
    }

    @Provides
    fun provideSubscribtionOnScheduler(scheduler: DefaultSubscribeScheduler): SubscribtionOnScheduler {
        return scheduler
    }
}