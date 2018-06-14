package com.tripl3dev.dataStore.movies

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.tripl3dev.domain.Entity.MoviesEntity

@Database(entities = [(MoviesEntity::class)], version = 2)
@TypeConverters(MoviesListConverter::class)
abstract class MoviesDb : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}