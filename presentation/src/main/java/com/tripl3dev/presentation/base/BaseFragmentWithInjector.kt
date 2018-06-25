package com.tripl3dev.presentation.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.tripl3dev.domain.managers.NetworkUtils
import com.tripl3dev.presentation.application.MyApplication
import com.tripl3dev.presentation.di.modules.viewModelDi.ViewModelFactory
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseFragmentWithInjector : Fragment(), NetworkUtils.ConnectionStatusCB {
    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var vm: BaseViewModel
    lateinit var disposal: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).networkComponent.inject(this)
        vm = ViewModelProviders.of(this, factory)[getActivityVM()]
        onConnectionChanged()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (NetworkUtils.getNetworkUtils().isNotConnected()) {
            onDisconnected()
        }
    }

    abstract fun getActivityVM(): Class<out BaseViewModel>


    override fun onConnected() {
        super.onConnected()
    }

    override fun onDisconnected() {
        super.onDisconnected()
    }

    fun onConnectionChanged() {
        disposal = NetworkUtils.getNetworkUtils().getNetworkStatus().subscribe {
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

    override fun onPause() {
        super.onPause()
        if (!disposal.isDisposed) {
            disposal.dispose()
        }
    }
}