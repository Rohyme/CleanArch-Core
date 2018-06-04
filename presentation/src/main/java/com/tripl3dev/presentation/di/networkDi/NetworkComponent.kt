package com.tripl3dev.presentation.di.networkDi

import com.tripl3dev.presentation.MainActivity
import com.tripl3dev.presentation.di.appDi.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,NetworkModule::class])
interface NetworkComponent {

 fun inject(activity:MainActivity)



    @Component.Builder
    interface NetworkBuilder {
        fun builder():NetworkComponent
        fun networkModule(networkModule: NetworkModule): NetworkBuilder
        fun appModule(applicationModule: ApplicationModule):NetworkBuilder
    }
}