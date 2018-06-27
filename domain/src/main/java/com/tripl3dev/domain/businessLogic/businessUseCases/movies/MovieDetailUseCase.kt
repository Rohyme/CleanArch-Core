package com.tripl3dev.domain.businessLogic.businessUseCases.movies

import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(private val repository: MoviesDetailsRepositoryI,
                                             observe: ObserveOnScheduler,
                                             subscribe: SubscribtionOnScheduler)
    : FlowableUseCase<MovieDetails, Int>(observe, subscribe) {
    override fun buildUseCaseObservable(params: Int?): Flowable<MovieDetails> {
        return repository.getMovieDetails(params!!)
    }

}