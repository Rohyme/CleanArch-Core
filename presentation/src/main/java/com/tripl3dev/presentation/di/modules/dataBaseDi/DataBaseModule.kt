package com.tripl3dev.presentation.di.modules.dataBaseDi

import android.arch.persistence.room.Room
import com.tripl3dev.dataStore.movies.MoviesDao
import com.tripl3dev.dataStore.movies.MoviesDb
import com.tripl3dev.dataStore.posts.PostsDao
import com.tripl3dev.dataStore.posts.PostsDataBase
import com.tripl3dev.presentation.application.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun providePostsDao(appContext: MyApplication): PostsDao {
        return Room.databaseBuilder(appContext, PostsDataBase::class.java, "allPosts_db").build().getPostDataBase()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(appContext: MyApplication): MoviesDao {
        return Room.databaseBuilder(appContext, MoviesDb::class.java, "movies_db").build().getMoviesDao()
    }

}