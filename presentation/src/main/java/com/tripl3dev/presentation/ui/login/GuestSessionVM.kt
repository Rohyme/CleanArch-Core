package com.tripl3dev.presentation.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.login.CheckLoggedInUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.login.CreateGuestSessionUseCase
import com.tripl3dev.domain.interactor.CustomSingleObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class GuestSessionVM @Inject constructor(val createUserUseCase: CreateGuestSessionUseCase,
                                         val checkLoggedInUseCase: CheckLoggedInUseCase
) : BaseViewModel() {

    private var loginObserve: MutableLiveData<Stateview> = MutableLiveData()
    private var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()


    fun loginAsGuest() {
        createUserUseCase.execute(CustomSingleObserver(object : SingleObserverCB<GuestEntity> {
            override fun onSuccess(t: GuestEntity) {
                loginObserve.postValue(Stateview.Success(t))
            }

            override fun onError(e: Throwable) {
                loginObserve.postValue(Stateview.Failure(e))
            }

            override fun onSubscribe() {
                super.onSubscribe()
                loginObserve.postValue(Stateview.Loading)
            }
        }))
    }

    fun checkUserLoggedIn() {
        checkLoggedInUseCase.execute(CustomSingleObserver(object : SingleObserverCB<Boolean> {
            override fun onSuccess(t: Boolean) {
                isLoggedIn.postValue(t)
            }

        }))
    }


    fun getLoginObserve(): LiveData<Stateview> {
        return loginObserve
    }

    fun isLoggedIn(): LiveData<Boolean> {
        return isLoggedIn
    }

}