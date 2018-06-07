package com.tripl3dev.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.ShowPostsUseCase
import com.tripl3dev.domain.managers.Stateview
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(val postsUseCase: ShowPostsUseCase) : ViewModel() {

    var showPosts: MutableLiveData<Stateview> = MutableLiveData()

    init {
        updatePostsList()
    }

    fun updatePostsList() {
        showPosts.postValue(Stateview.Loading(true))
        postsUseCase.execute(object : DisposableSingleObserver<ArrayList<PostEntity>>() {
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

        })
    }

    fun getShowPostUpdated(): LiveData<Stateview> {
        return showPosts
    }
}