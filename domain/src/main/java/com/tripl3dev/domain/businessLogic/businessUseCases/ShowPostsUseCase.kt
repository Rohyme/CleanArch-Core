package com.tripl3dev.domain.businessLogic.businessUseCases

import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.domain.interactor.SingleUseCase
import com.tripl3dev.domain.viewState.BaseStates
import io.reactivex.Single
import javax.inject.Inject

class ShowPostsUseCase @Inject constructor(val repositoryI: PostsRepositoryI,
                                           val observeOnScheduler: ObserveOnScheduler,
                                           val subscribeScheduler: SubscribtionOnScheduler)
    : SingleUseCase<ArrayList<PostEntity>, Nothing>(observeOnScheduler, subscribeScheduler) {

    override fun buildUseCaseObservable(params: Nothing?): Single<ArrayList<PostEntity>> {
        return repositoryI.getPosts().doOnSuccess { postsList ->
            if (postsList.isEmpty()) BaseStates.NoDataFound(true)
            else BaseStates.FetchedSuccessfully(postsList)
                }
                .doOnError({ error ->
                    BaseStates.ErrorHappened(error)
                })
                .doOnSubscribe({
                    BaseStates.Loading(true)
                })
    }
}