package com.tripl3dev.presentation.application

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.presentation.di.components.ApplicationComponent
import com.tripl3dev.presentation.di.components.DaggerApplicationComponent
import com.tripl3dev.presentation.di.components.DaggerNetworkComponent
import com.tripl3dev.presentation.di.components.NetworkComponent
import com.tripl3dev.presentation.utils.connectivity.ConnectivityReciever
import com.tripl3dev.prettystates.StatesConfigFactory


class MyApplication : Application() {

    private lateinit var appComponent: ApplicationComponent
    lateinit var networkComponent: NetworkComponent
    private lateinit var networkDetector: ConnectivityReciever

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
                .applicationContext(this)
                .builder()
        networkComponent = DaggerNetworkComponent.builder()
                .application(this)
                .baseUrl(Constants.BASE_URL)
                .builder()
        networkDetector = ConnectivityReciever()
        val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkDetector, intent)
        StatesConfigFactory.intialize().initDefaultViews()
    }

    override fun onTerminate() {
        super.onTerminate()
        networkDetector.abortBroadcast()
    }
}