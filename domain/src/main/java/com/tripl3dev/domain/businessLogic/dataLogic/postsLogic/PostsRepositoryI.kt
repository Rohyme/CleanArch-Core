package com.tripl3dev.domain.businessLogic.dataLogic.postsLogic

import com.tripl3dev.domain.Entity.PostEntity
import io.reactivex.Single

interface PostsRepositoryI {

    fun getPosts(): Single<ArrayList<PostEntity>>
}