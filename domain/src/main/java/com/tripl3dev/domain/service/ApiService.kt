package com.tripl3dev.domain.service

import com.tripl3dev.domain.Entity.GuestEntity
import com.tripl3dev.domain.Entity.MoviesEntity
import com.tripl3dev.domain.Entity.PostEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    fun getPosts(): Single<ArrayList<PostEntity>>


    @GET("authentication/guest_session/new")
    fun getGuestToken(@Query("api_key") apiToken: String): Single<GuestEntity>


    @GET("/movie/popular")
    fun getPopularMovies(@Query("api_key") apiToken: String, @Query("page") pageNum: Int): Single<MoviesEntity>

    @GET("/movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiToken: String, @Query("page") pageNum: Int): Single<MoviesEntity>

    @GET("/movie/latest")
    fun getLatestMovies(@Query("api_key") apiToken: String): Single<MoviesEntity>

}