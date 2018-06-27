package com.tripl3dev.domain.businessLogic.businessUseCases.movies

import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class ToggleMovieInFavouriteUseCase @Inject constructor(private val repository: MoviesDetailsRepositoryI,
                                                        observe: ObserveOnScheduler,
                                                        subscribe: SubscribtionOnScheduler) : CompletableUseCase<Int>(observe, subscribe) {
    override fun buildUseCaseObservable(params: Int): Completable {
        return Completable.create {
            repository.toggleMovieInFav(params)
            it.onComplete()
            print("ToggleUseCase We are here $params")
        }
    }

}