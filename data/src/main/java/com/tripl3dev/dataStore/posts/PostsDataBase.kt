package com.tripl3dev.dataStore.posts

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tripl3dev.domain.Entity.PostEntity


@Database(entities = [(PostEntity::class)], version = 1)
abstract class PostsDataBase : RoomDatabase() {
    abstract fun getPostDataBase(): PostsDao
}