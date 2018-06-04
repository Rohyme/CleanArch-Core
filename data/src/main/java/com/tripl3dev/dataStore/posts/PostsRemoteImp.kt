package com.tripl3dev.dataStore.posts

import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRemoteI
import com.tripl3dev.domain.service.ApiService
import io.reactivex.Single
import javax.inject.Inject

 class PostsRemoteImp @Inject constructor(val apiService: ApiService) :PostsRemoteI{
    override fun getPosts(): Single<ArrayList<PostEntity>> {
        return apiService.getPosts()
    }

}