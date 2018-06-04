package com.tripl3dev.presentation.di.appDi

import android.content.Context
import com.tripl3dev.presentation.application.MyApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun injectApp(myApp: MyApplication)
    @Component.Builder
    interface Builder {
        fun builder() :ApplicationComponent
        fun applicationModule(appModule: ApplicationModule):Builder
        @BindsInstance
        fun applicationContext(appContext: MyApplication):Builder
    }
}