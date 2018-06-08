package com.tripl3dev.presentation.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.CreateGuestSessionUseCase
import com.tripl3dev.domain.interactor.CustomSingleObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class GuestSessionVM @Inject constructor(val useCase: CreateGuestSessionUseCase) : BaseViewModel() {


    private var loginObserve: MutableLiveData<Stateview> = MutableLiveData()


    fun loginUpdated() {
        useCase.execute(CustomSingleObserver(object : SingleObserverCB<GuestEntity> {
            override fun onSuccess(t: GuestEntity) {
                loginObserve.postValue(Stateview.Success(t))
            }

            override fun onError(e: Throwable) {
                loginObserve.postValue(Stateview.Failure(e))
            }
        }))
    }


    fun getLoginObserve(): LiveData<Stateview> {
        return loginObserve
    }


}