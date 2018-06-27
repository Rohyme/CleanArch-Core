package com.tripl3dev.presentation.ui.movieDetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.MovieDetailUseCase
import com.tripl3dev.domain.businessLogic.businessUseCases.movies.ToggleMovieInFavouriteUseCase
import com.tripl3dev.domain.interactor.CustomFlowableObserver
import com.tripl3dev.domain.interactor.SingleObserverCB
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class MovieDetailsVM @Inject constructor(private val movieDetailUseCase: MovieDetailUseCase,
                                         private val toggleMovieInFavouriteUseCase: ToggleMovieInFavouriteUseCase) : BaseViewModel() {
    private var movieDetails: MutableLiveData<Stateview> = MutableLiveData()
    private var isFavourited: MutableLiveData<Int> = MutableLiveData()


    fun fetchMovieDetails(movieInt: Int) {
        movieDetailUseCase.execute(CustomFlowableObserver(object : SingleObserverCB<MovieDetails> {
            override fun onSuccess(t: MovieDetails) {
                movieDetails.postValue(Stateview.Success(t))
                isFavourited.postValue(t.favourite)
            }

            override fun onError(e: Throwable) {
                movieDetails.postValue(Stateview.Failure(e))
            }

            override fun onSubscribe() {
                super.onSubscribe()
                movieDetails.postValue(Stateview.Loading)
            }
        }), movieInt)
    }


    fun getMovieDetails(): LiveData<Stateview> {
        return movieDetails
    }

    fun movieIsFavourite(): LiveData<Int> {
        return isFavourited
    }


    fun toggleFavourite(movieId: Int, isFavourite: Int) {
        val favourite = if (isFavourite == 1) 2 else 1
        toggleMovieInFavouriteUseCase.execute(movieId).subscribe {
            isFavourited.postValue(favourite)
        }
    }

    fun retry() {
        movieDetailUseCase.retry()
    }

    override fun onCleared() {
        super.onCleared()
        movieDetailUseCase.unSubscribe()
        toggleMovieInFavouriteUseCase.unSubscribe()
    }

}