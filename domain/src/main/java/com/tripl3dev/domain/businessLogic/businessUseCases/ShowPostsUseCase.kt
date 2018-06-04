package com.tripl3dev.domain.businessLogic.businessUseCases

import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ShowPostsUseCase @Inject constructor(val repository: PostsRepositoryI,
                                           private val observeOnScheduler: ObserveOnScheduler,
                                           private val subscribeScheduler: SubscribtionOnScheduler)
    : SingleUseCase<ArrayList<PostEntity>, Nothing>(observeOnScheduler, subscribeScheduler) {

    override fun buildUseCaseObservable(params: Nothing?): Single<ArrayList<PostEntity>> {
        return repository.getPosts()
    }
}