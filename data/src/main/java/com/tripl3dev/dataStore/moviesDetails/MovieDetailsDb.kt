package com.tripl3dev.dataStore.moviesDetails

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tripl3dev.domain.Entity.MovieDetails

@Database(entities = [MovieDetails::class], version = 1)
abstract class MovieDetailsDb : RoomDatabase() {
    abstract fun getMovieDetailsDao(): MoviesDetailsDao
}