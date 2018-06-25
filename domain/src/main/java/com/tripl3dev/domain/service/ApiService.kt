package com.tripl3dev.domain.service

import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.Entity.MovieDetails
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.Entity.PostEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    fun getPosts(): Single<ArrayList<PostEntity>>


    @GET("/3/authentication/guest_session/new")
    fun getGuestToken(@Query("api_key") apiToken: String): Single<GuestEntity>


    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("api_key") apiToken: String, @Query("page") pageNum: Int): Single<MoviesEntity>

    @GET("/3/movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiToken: String, @Query("page") pageNum: Int): Single<MoviesEntity>

    @GET("/3/movie/now_playing")
    fun getLatestMovies(@Query("api_key") apiToken: String, @Query("page") pageNum: Int): Single<MoviesEntity>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiToken: String): Single<MovieDetails>

}