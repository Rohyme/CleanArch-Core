package com.tripl3dev.presentation.di.repositoriesDi

import com.tripl3dev.dataStore.posts.PostsRepositoryImp
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import com.tripl3dev.domain.executer.ObserveOnScheduler
import com.tripl3dev.domain.executer.SubscribtionOnScheduler
import com.tripl3dev.schedulers.DefaultObserveScheduler
import com.tripl3dev.schedulers.DefaultSubscribeScheduler
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {
    @Provides
    fun providePostsRepositoryI(postsRepository: PostsRepositoryImp): PostsRepositoryI {
        return postsRepository
    }

    @Provides
    fun provideObserveOnScheduler(scheduler: DefaultObserveScheduler): ObserveOnScheduler {
        return scheduler
    }

    @Provides
    fun provideSubscribtionOnScheduler(scheduler: DefaultSubscribeScheduler): SubscribtionOnScheduler {
        return scheduler
    }
}

