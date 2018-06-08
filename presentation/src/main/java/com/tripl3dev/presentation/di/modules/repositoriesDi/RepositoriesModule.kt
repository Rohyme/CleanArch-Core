package com.tripl3dev.presentation.di.modules.repositoriesDi

import com.tripl3dev.dataStore.login.LoginRepositoryImp
import com.tripl3dev.dataStore.posts.PostsRepositoryImp
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepository
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoriesModule {


    @Binds
    abstract fun providePostsRepositoryI(postsRepository: PostsRepositoryImp): PostsRepositoryI


    @Binds
    abstract fun provideLoginRepositoryI(loginRepo: LoginRepositoryImp): LoginRepository


}

