package com.tripl3dev.domain.businessLogic.businessUseCases.movies

import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetLatestMoviesUseCase @Inject constructor(val repository: MoviesRepositoryI,
                                                 observe: ObserveOnScheduler,
                                                 subscribe: SubscribtionOnScheduler)
    : SingleUseCase<MoviesEntity, Int>(observe, subscribe) {
    override fun buildUseCaseObservable(params: Int?): Single<MoviesEntity> {
        return repository.getLatestMovies(params!!)
    }

}