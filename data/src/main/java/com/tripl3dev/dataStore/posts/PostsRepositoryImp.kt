package com.tripl3dev.dataStore.posts

import android.util.Log
import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsRepositoryI
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImp @Inject constructor(private val factory:PostDataStoreFactory) :PostsRepositoryI {
    override fun getPosts(): Single<ArrayList<PostEntity>> {
        Log.e("RepositoryImp", "Da5al Hena Ahooo")
        return  factory.retrieveDataStore().getPosts()
                .doOnSuccess {
                    if (factory.fromRemote) {
                        factory.retrieveCasheDataStore().savePosts(it)
                    }
                }
    }
}