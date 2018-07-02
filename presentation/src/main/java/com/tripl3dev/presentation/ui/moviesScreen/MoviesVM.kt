package com.tripl3dev.presentation.ui.moviesScreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.dataStore.movies.MoviesRepositoryImp
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetLatestMoviesUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetPopularMoviesUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.GetTopRatedMoviesUseCase
import com.tripl3dev.domain.interactor.CustomFlowableObserver
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
    private var moviesType: MutableLiveData<Int> = MutableLiveData()

    init {
        moviesType.postValue(MoviesRepositoryImp.LATEST_MOVIES)
    }

    var currentList: ArrayList<MoviesEntity.Movie> = ArrayList()
    var currentPage: Int = 1

    fun getLatestMovies(): MutableLiveData<Stateview> {
        return latestMovies
    }

    fun getPopularMovies(): LiveData<Stateview> {
        return popularMovies
    }

    fun getTopRated(): LiveData<Stateview> {
        return topRatedMovies
    }

    fun getMoviesType(): LiveData<Int> {
        return moviesType
    }

    fun setMoviesType(type: Int) {
        moviesType.postValue(type)
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


    fun fetchLatestMovies() {
        latestMoviesUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                loadData(latestMovies, t)
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

            override fun onEmptyList() {
                latestMovies.postValue(Stateview.HasNoData)
            }
        }), currentPage)
    }

    fun fetchPopularMovies() {
        popularMoviesUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                loadData(popularMovies, t)
            }

            override fun onSubscribe() {
                super.onSubscribe()
                popularMovies.postValue(Stateview.Loading)
            }

            override fun onError(e: Throwable) {
                super.onError(e)

                popularMovies.postValue(Stateview.Failure(e))
            }
        }), currentPage)

    }

    fun fetchtopRatedMovies() {
        topRatedMoviesUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<MoviesEntity> {
            override fun onSuccess(t: MoviesEntity) {
                loadData(topRatedMovies, t)
            }

            override fun onSubscribe() {
                super.onSubscribe()
                topRatedMovies.postValue(Stateview.Loading)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                topRatedMovies.postValue(Stateview.Failure(e))

            }
        }), currentPage)

    }

    fun loadData(liveData: MutableLiveData<Stateview>, t: MoviesEntity) {
        if (currentPage != 1) {
            currentList.addAll(t.moviesList)
            liveData.postValue(Stateview.Success(currentList))
        } else {
            currentList = ArrayList(t.moviesList)
            liveData.postValue(Stateview.Success(currentList))
        }
    }

    override fun onCleared() {
        super.onCleared()
        topRatedMoviesUseCase.unSubscribe()
        popularMoviesUseCase.unSubscribe()
        latestMoviesUseCase.unSubscribe()
    }


    fun resetList() {
        currentPage = 1
        currentList.clear()
    }


}