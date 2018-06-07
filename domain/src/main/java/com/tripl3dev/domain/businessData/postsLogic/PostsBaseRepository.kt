package com.tripl3dev.domain.businessData.postsLogic

import com.tripl3dev.domain.Entity.PostEntity
import io.reactivex.Single

interface PostsBaseStore {

    fun getPosts():Single<ArrayList<PostEntity>>
}