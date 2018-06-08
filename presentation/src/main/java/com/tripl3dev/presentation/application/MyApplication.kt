package com.tripl3dev.presentation.application

import android.app.Application
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.presentation.di.components.ApplicationComponent
import com.tripl3dev.presentation.di.components.DaggerApplicationComponent
import com.tripl3dev.presentation.di.components.DaggerNetworkComponent
import com.tripl3dev.presentation.di.components.NetworkComponent


class MyApplication : Application() {

    lateinit var appComponent: ApplicationComponent
    lateinit var networkComponent: NetworkComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
                .applicationContext(this)
                .builder()
        networkComponent = DaggerNetworkComponent.builder()
                .application(this)
                .baseUrl(Constants.BASE_URL)
                .builder()

    }
}