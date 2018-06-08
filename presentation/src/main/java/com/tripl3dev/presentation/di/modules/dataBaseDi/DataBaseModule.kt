package com.tripl3dev.presentation.di.modules.dataBaseDi

import android.arch.persistence.room.Room
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

}