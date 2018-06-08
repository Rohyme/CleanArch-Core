package com.tripl3dev.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.ShowPostsUseCase
import com.tripl3dev.domain.interactor.CustomSingleObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(val postsUseCase: ShowPostsUseCase) : BaseViewModel() {

    var showPosts: MutableLiveData<Stateview> = MutableLiveData()

    init {
        updatePostsList()
    }

    fun updatePostsList() {
//        showPosts.postValue(Stateview.Loading(true))
        postsUseCase.execute(CustomSingleObserver(object : SingleObserverCB<ArrayList<PostEntity>> {
            override fun onSuccess(t: ArrayList<PostEntity>) {
                showPosts.postValue(Stateview.Success(t))
            }

            override fun onHttpError(errorCode: Int, message: String) {
                showPosts.postValue(Stateview.ServiceError(errorCode))
            }

            override fun onThrowableError(e: Throwable) {
                super.onThrowableError(e)
            }

            override fun onError(e: Throwable) {
                showPosts.postValue(Stateview.Failure(e))
            }

            override fun onEmptyList() {
                showPosts.postValue(Stateview.HasNoData(true))
            }

            override fun onSubscribe() {
                showPosts.postValue(Stateview.Loading(true))

            }
        }))
    }
/*
    object : DisposableSingleObserver<ArrayList<PostEntity>>() {
        override fun onSuccess(t: ArrayList<PostEntity>) {
            if (t.isEmpty()) showPosts.postValue(Stateview.HasNoData(true))
            else showPosts.postValue(Stateview.Success(t))
        }
        override fun onError(e: Throwable) {
            showPosts.postValue(Stateview.Failure(e))
            if (e is HttpException) {
                showPosts.postValue(Stateview.ServiceError(e.code()))
            } else
                showPosts.postValue(Stateview.Failure(e))
        }

    }*/

    fun getShowPostUpdated(): LiveData<Stateview> {
        return showPosts
    }
}