package com.tripl3dev.dataStore.tvShows

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.tripl3dev.domain.Entity.TvShowsEntity

@Database(entities = [TvShowsEntity::class], version = 1)
@TypeConverters(TvShowsConverter::class)
abstract class TvShowsDB : RoomDatabase() {
    abstract fun getTvShowDao(): TvShowsDao
}