package com.tripl3dev.presentation.di.modules.repositoriesDi

import com.tripl3dev.dataStore.login.LoginRepositoryImp
import com.tripl3dev.dataStore.posts.PostsRepositoryImp
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepositoryI
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {
    @Provides
    fun providePostsRepositoryI(postsRepository: PostsRepositoryImp): PostsRepositoryI {
        return postsRepository
    }


    @Provides
    fun provideLoginRepositoryI(postsRepository: LoginRepositoryImp): LoginRepositoryI {
        return postsRepository
    }


}

