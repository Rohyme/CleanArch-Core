package com.tripl3dev.presentation.ui.moviesScreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetLatestMoviesUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetPopularMoviesUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetTopRatedMoviesUseCase
import com.tripl3dev.domain.interactor.CustomSingleObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class MoviesVM @Inject constructor(private val latestMoviesUseCase: GetLatestMoviesUseCase,
                                   private val popularMoviesUseCase: GetPopularMoviesUseCase,
                                   private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase) : BaseViewModel() {

    private var latestMovies: MutableLiveData<Stateview> = MutableLiveData()
    private var popularMovies: MutableLiveData<Stateview> = MutableLiveData()
    private var topRatedMovies: MutableLiveData<Stateview> = MutableLiveData()

    fun getLatestMovies(): LiveData<Stateview> {
        return latestMovies
    }

    fun getPopularMovies(): LiveData<Stateview> {
        return popularMovies
    }

    fun getTopRated(): LiveData<Stateview> {
        return topRatedMovies
    }


    fun fetchLatestMovies() {
        latestMoviesUseCase.execute(CustomSingleObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                latestMovies.postValue(Stateview.Success(t.moviesList))
            }

            override fun onSubscribe() {
                super.onSubscribe()
                latestMovies.postValue(Stateview.Loading)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                latestMovies.postValue(Stateview.Failure(e))

            }
        }))
    }

    fun fetchPopularMovies(pageNum: Int) {
        popularMoviesUseCase.execute(CustomSingleObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                popularMovies.postValue(Stateview.Success(t.moviesList))
            }

            override fun onSubscribe() {
                super.onSubscribe()
                if (pageNum == 1)
                    popularMovies.postValue(Stateview.Loading)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                popularMovies.postValue(Stateview.Failure(e))

            }
        }), pageNum)

    }

    fun fetchtopRatedMovies(pageNum: Int) {
        topRatedMoviesUseCase.execute(CustomSingleObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                topRatedMovies.postValue(Stateview.Success(t.moviesList))
            }

            override fun onSubscribe() {
                super.onSubscribe()
                if (pageNum == 1)
                    topRatedMovies.postValue(Stateview.Loading)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                topRatedMovies.postValue(Stateview.Failure(e))

            }
        }), pageNum)

    }







}