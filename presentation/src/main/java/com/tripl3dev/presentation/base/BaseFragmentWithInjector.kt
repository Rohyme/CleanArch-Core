package com.tripl3dev.presentation.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.tripl3dev.presentation.application.MyApplication
import com.tripl3dev.presentation.di.modules.viewModelDi.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragmentWithInjector : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var vm: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).networkComponent.inject(this)
        vm = ViewModelProviders.of(this, factory)[getActivityVM()]
    }


    abstract fun getActivityVM(): Class<out BaseViewModel>

}