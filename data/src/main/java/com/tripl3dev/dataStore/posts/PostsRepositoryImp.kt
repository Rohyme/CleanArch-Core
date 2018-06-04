package com.tripl3dev.dataStore.posts

import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import io.reactivex.Single
import javax.inject.Inject

class PostsRepositoryImp @Inject constructor(private val factory:PostDataStoreFactory) :PostsRepositoryI {
    override fun getPosts(): Single<ArrayList<PostEntity>> {
        return  factory.retrieveDataStore().getPosts()
    }
}