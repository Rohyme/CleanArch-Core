package com.tripl3dev.presentation.ui.peopleScreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.ActorEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.people.ShowPeopleUseCase
import com.tripl3dev.domain.interactor.CustomFlowableObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class PeopleVM @Inject constructor(private val showActorsUseCase: ShowPeopleUseCase) : BaseViewModel() {

    var currentPage = 1
    var currentList: ArrayList<ActorEntity.Actor> = ArrayList()
    private var actorsList: MutableLiveData<Stateview> = MutableLiveData()


    fun fetchActorsData() {
        showActorsUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<ActorEntity> {
            override fun onSuccess(t: ActorEntity) {
                updateList(t.actors)
                actorsList.postValue(Stateview.Success(currentList))
            }

            override fun onError(e: Throwable) {
                actorsList.postValue(Stateview.Failure(e))
            }

            override fun onEmptyList() {
                actorsList.postValue(Stateview.HasNoData)
            }

            override fun onSubscribe() {
                actorsList.postValue(Stateview.Loading)
            }
        }), currentPage)
    }

    private fun updateList(list: List<ActorEntity.Actor>) {
        if (currentPage > 1) {
            currentList.addAll(list)
        } else {
            currentList = ArrayList(list)
        }
    }

    fun getActorsList(): LiveData<Stateview> {
        return actorsList
    }

    fun retryGettingActors() {
        showActorsUseCase.retry()
    }
}