package com.tripl3dev.presentation.di.modules.repositoriesDi

import com.tripl3dev.dataStore.login.LoginRepositoryImp
import com.tripl3dev.dataStore.movies.MoviesRepositoryImp
import com.tripl3dev.dataStore.moviesDetails.MovieDetailsRepositoryImp
import com.tripl3dev.dataStore.posts.PostsRepositoryImp
import com.tripl3dev.domain.businessLogic.dataLogic.loginLogic.LoginRepositoryI
import com.tripl3dev.domain.businessLogic.dataLogic.moviesDetails.MoviesDetailsRepositoryI
import com.tripl3dev.domain.businessLogic.dataLogic.moviesLogic.MoviesRepositoryI
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Provides
    @Singleton
    fun providePostsRepositoryI(postsRepository: PostsRepositoryImp): PostsRepositoryI {
        return postsRepository
    }


    @Provides
    @Singleton
    fun provideLoginRepositoryI(postsRepository: LoginRepositoryImp): LoginRepositoryI {
        return postsRepository
    }


    @Provides
    @Singleton
    fun provideMoviesRepositoryI(repository: MoviesRepositoryImp): MoviesRepositoryI {
        return repository
    }


    @Provides
    @Singleton
    fun provideMovieDetailsRepositoryI(repository: MovieDetailsRepositoryImp): MoviesDetailsRepositoryI {
        return repository
    }


}

