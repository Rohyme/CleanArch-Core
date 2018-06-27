package com.tripl3dev.dataStore.moviesDetails

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tripl3dev.domain.Entity.MovieDetails
import io.reactivex.Single

@Dao
interface MoviesDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieDetails)

    @Query("SELECT * from  movies_details  where  movie_id = :movieId")
    fun getMovieDetails(movieId: Int): Single<MovieDetails>


    @Query("UPDATE movies_details set favourite = CASE  When favourite = 1 THEN  0 ELSE 1 end  where movie_id=:movieId")
    fun toggleFavourite(movieId: Int)

}