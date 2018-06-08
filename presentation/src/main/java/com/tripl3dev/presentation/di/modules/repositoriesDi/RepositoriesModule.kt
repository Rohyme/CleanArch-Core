package com.tripl3dev.presentation.di.modules.repositoriesDi

import com.tripl3dev.dataStore.posts.PostsRepositoryImp
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {
    @Provides
    fun providePostsRepositoryI(postsRepository: PostsRepositoryImp): PostsRepositoryI {
        return postsRepository
    }


}

