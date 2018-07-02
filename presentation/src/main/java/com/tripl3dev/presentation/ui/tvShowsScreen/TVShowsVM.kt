package com.tripl3dev.presentation.ui.tvShowsScreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.TvShowsEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.tvShows.ShowTvShowsUseCase
import com.tripl3dev.domain.interactor.CustomFlowableObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class TVShowsVM @Inject constructor(val showTvShowsUseCase: ShowTvShowsUseCase) : BaseViewModel() {

    var currentPage: Int = 1
    var currentList: ArrayList<TvShowsEntity.tvShow> = ArrayList()
    private var tvShowsList: MutableLiveData<Stateview> = MutableLiveData()


    fun fetchTvShows() {
        showTvShowsUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<TvShowsEntity> {
            override fun onSuccess(t: TvShowsEntity) {
                updateList(t.tvShows)
                tvShowsList.postValue(Stateview.Success(currentList))
            }

            override fun onError(e: Throwable) {
                tvShowsList.postValue(Stateview.Failure(e))
            }

            override fun onEmptyList() {
                tvShowsList.postValue(Stateview.HasNoData)
            }

            override fun onSubscribe() {
                tvShowsList.postValue(Stateview.Loading)
            }
        }), currentPage)
    }

    private fun updateList(list: List<TvShowsEntity.tvShow>) {
        if (currentPage > 1) {
            currentList.addAll(list)
        } else {
            currentList = ArrayList(list)
        }
    }

    fun getTvShows(): LiveData<Stateview> {
        return tvShowsList
    }

    fun retryGettingTvShows() {
        showTvShowsUseCase.retry()
    }


}