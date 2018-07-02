package com.tripl3dev.presentation.di.modules.dataBaseDi

import android.arch.persistence.room.Room
import com.tripl3dev.dataStore.movies.MoviesDao
import com.tripl3dev.dataStore.movies.MoviesDb
import com.tripl3dev.dataStore.moviesDetails.MovieDetailsDb
import com.tripl3dev.dataStore.moviesDetails.MoviesDetailsDao
import com.tripl3dev.dataStore.people.PeopleDB
import com.tripl3dev.dataStore.people.PeopleDao

import com.tripl3dev.dataStore.tvShows.TvShowsDB
import com.tripl3dev.dataStore.tvShows.TvShowsDao
import com.tripl3dev.presentation.application.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {


    @Provides
    @Singleton
    fun provideMoviesDao(appContext: MyApplication): MoviesDao {
        return Room.databaseBuilder(appContext, MoviesDb::class.java, "movies_db").fallbackToDestructiveMigration()
                .build().getMoviesDao()
    }


    @Provides
    @Singleton
    fun provideMovieDetailsDao(appContext: MyApplication): MoviesDetailsDao {
        return Room.databaseBuilder(appContext, MovieDetailsDb::class.java, "movie_details_db").fallbackToDestructiveMigration()
                .build().getMovieDetailsDao()
    }


    @Provides
    @Singleton
    fun providePeopleDao(appContext: MyApplication): PeopleDao {
        return Room.databaseBuilder(appContext, PeopleDB::class.java, "people_db").fallbackToDestructiveMigration()
                .build().getPeopleDao()
    }


    @Provides
    @Singleton
    fun provideTvShowsDao(appContext: MyApplication): TvShowsDao {
        return Room.databaseBuilder(appContext, TvShowsDB::class.java, "tv_shows_db").fallbackToDestructiveMigration()
                .build().getTvShowDao()
    }

}