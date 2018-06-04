package com.tripl3dev.domain.service

import com.tripl3dev.domain.Entity.PostEntity
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("/post")
    fun getPosts(): Single<ArrayList<PostEntity>>

}