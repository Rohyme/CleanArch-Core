package com.tripl3dev.presentation.di.components

import com.tripl3dev.presentation.application.MyApplication
import com.tripl3dev.presentation.base.BaseActivityWithInjector
import com.tripl3dev.presentation.di.modules.appDi.ApplicationModule
import com.tripl3dev.presentation.di.modules.dataBaseDi.DataBaseModule
import com.tripl3dev.presentation.di.modules.networkDi.NetworkModule
import com.tripl3dev.presentation.di.modules.repositoriesDi.RepositoriesModule
import com.tripl3dev.presentation.di.modules.schedulersDi.SchedulersModule
import com.tripl3dev.presentation.di.modules.sharedPreferenceDi.SharedPreferenceModule
import com.tripl3dev.presentation.di.modules.viewModelDi.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ViewModelModule::class, RepositoriesModule::class
    , DataBaseModule::class, SchedulersModule::class, SharedPreferenceModule::class])
interface NetworkComponent {
    fun inject(mainActivity: BaseActivityWithInjector)

    @Component.Builder
    interface NetworkBuilder {
        fun builder(): NetworkComponent
        @BindsInstance
        fun application(app: MyApplication): NetworkBuilder

        @BindsInstance
        fun baseUrl(baseUrl: String): NetworkBuilder
    }
}