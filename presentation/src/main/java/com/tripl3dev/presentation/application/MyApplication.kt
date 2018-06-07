package com.tripl3dev.presentation.application

import android.app.Application
import com.tripl3dev.presentation.di.appDi.ApplicationComponent
import com.tripl3dev.presentation.di.appDi.ApplicationModule
import com.tripl3dev.presentation.di.appDi.DaggerApplicationComponent
import com.tripl3dev.presentation.di.networkDi.DaggerNetworkComponent
import com.tripl3dev.presentation.di.networkDi.NetworkComponent
import com.tripl3dev.presentation.di.networkDi.NetworkModule
import com.tripl3dev.prettystates.StatesConfigFactory

class MyApplication : Application() {

    lateinit var appComponent: ApplicationComponent
    lateinit var networkComponent: NetworkComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .applicationContext(this)
                .builder()
        networkComponent = DaggerNetworkComponent.builder()
                .appModule(ApplicationModule(this))
                .networkModule(NetworkModule("https://jsonplaceholder.typicode.com/"))
                .builder()
        StatesConfigFactory.intialize().initDefaultViews(this)


    }
}