package com.tripl3dev.presentation.di.components

import com.tripl3dev.presentation.application.MyApplication
import com.tripl3dev.presentation.di.appDi.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun injectApp(myApp: MyApplication)
    @Component.Builder
    interface Builder {
        fun builder(): ApplicationComponent
        @BindsInstance
        fun applicationContext(appContext: MyApplication): Builder
    }
}