package com.tripl3dev.presentation.ui.moviesScreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetLatestMoviesUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetPopularMoviesUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetTopRatedMoviesUseCase
import com.tripl3dev.domain.interactor.CustomFlowableObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MoviesVM @Inject constructor(private val latestMoviesUseCase: GetLatestMoviesUseCase,
                                   private val popularMoviesUseCase: GetPopularMoviesUseCase,
                                   private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase) : BaseViewModel() {


    private var latestPublisher: PublishSubject<Long> = PublishSubject.create()
    private var latestMovies: MutableLiveData<Stateview> = MutableLiveData()
    private var popularMovies: MutableLiveData<Stateview> = MutableLiveData()
    private var topRatedMovies: MutableLiveData<Stateview> = MutableLiveData()

    fun getLatestMovies(): MutableLiveData<Stateview> {
        return latestMovies
    }

    fun getPopularMovies(): LiveData<Stateview> {
        return popularMovies
    }

    fun getTopRated(): LiveData<Stateview> {
        return topRatedMovies
    }

    fun retryLatestMovies() {
        latestMoviesUseCase.retry()
    }

    fun retryPopularMovies() {
        popularMoviesUseCase.retry()
    }

    fun retryTopRatedMovies() {
        topRatedMoviesUseCase.retry()
    }

    fun fetchLatestMovies(pageNum: Int) {
        latestMoviesUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                latestMovies.postValue(Stateview.Success(t.moviesList))
            }

            override fun onSubscribe() {
                super.onSubscribe()
                latestMovies.postValue(Stateview.Loading)
            }

            override fun hasPreviousData(): Boolean {
                return latestMovies.value is Stateview.Success<*>
            }


            override fun onError(e: Throwable) {
                super.onError(e)
                latestMovies.postValue(Stateview.Failure(e))

            }

        }), pageNum)
    }

    fun fetchPopularMovies(pageNum: Int) {
        popularMoviesUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                popularMovies.postValue(Stateview.Success(t.moviesList))
            }

            override fun onSubscribe() {
                super.onSubscribe()
                popularMovies.postValue(Stateview.Loading)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                popularMovies.postValue(Stateview.Failure(e))
            }
        }), pageNum)

    }

    fun fetchtopRatedMovies(pageNum: Int) {
        topRatedMoviesUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                topRatedMovies.postValue(Stateview.Success(t.moviesList))
            }

            override fun onSubscribe() {
                super.onSubscribe()
//                if (pageNum == 1)
                topRatedMovies.postValue(Stateview.Loading)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                topRatedMovies.postValue(Stateview.Failure(e))

            }
        }), pageNum)

    }




}