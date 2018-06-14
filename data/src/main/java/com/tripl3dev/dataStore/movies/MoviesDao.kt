package com.tripl3dev.dataStore.movies

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tripl3dev.domain.Entity.MoviesEntity
import io.reactivex.Single

@Dao
interface MoviesDao {
    @Query("SELECT * FROM  movies_table WHERE page_num = 1 AND movies_type = -10")
    fun getPopularMovies(): Single<MoviesEntity>

    @Query("SELECT * FROM  movies_table WHERE page_num = 1 AND movies_type = -11")
    fun getLatestMovies(): Single<MoviesEntity>

    @Query("SELECT * FROM  movies_table WHERE page_num = 1 AND movies_type = -12")
    fun getTopRatedMovies(): Single<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesList(movies: MoviesEntity)

    @Query("SELECT COUNT(*) FROM movies_table WHERE page_num =:page AND movies_type =:type ")
    fun checkMoviesExist(page: Int, type: Int): Int


}