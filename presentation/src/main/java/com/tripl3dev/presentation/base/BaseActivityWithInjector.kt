package com.tripl3dev.presentation.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tripl3dev.domain.managers.NetworkUtils
import com.tripl3dev.presentation.application.MyApplication
import com.tripl3dev.presentation.di.modules.viewModelDi.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivityWithInjector : AppCompatActivity(), NetworkUtils.ConnectionStatusCB {

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var vm: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).networkComponent.inject(this)
        vm = ViewModelProviders.of(this, factory)[getActivityVM()]
        onConnectionChanged()
    }


    abstract fun getActivityVM(): Class<out BaseViewModel>


    override fun onConnected() {
        super.onConnected()
    }

    override fun onDisconnected() {
        super.onDisconnected()
    }

    fun onConnectionChanged() {
        NetworkUtils.getNetworkUtils().getNetworkStatus().subscribe {
            when (it) {
                NetworkUtils.CONNECTED -> {
                    onConnected()
                }
                NetworkUtils.DISCONNECTED -> {
                    onDisconnected()
                }
            }
        }
    }
}