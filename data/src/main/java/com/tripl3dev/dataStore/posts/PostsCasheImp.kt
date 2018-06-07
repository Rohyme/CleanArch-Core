package com.tripl3dev.dataStore.posts

import android.util.Log
import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsCasheI
import com.tripl3dev.domain.managers.CasheUtil
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsCasheImp @Inject constructor() : PostsCasheI {

    var isCashed: Boolean = false
        get() = postsSingle.isNotEmpty()
    private var mLastCacheTime: Long = 0

    var postsSingle: ArrayList<PostEntity> = ArrayList()


    override fun savePosts(posts: ArrayList<PostEntity>) {
        Completable.create {
            postsSingle.clear()
            postsSingle.addAll(posts)
            it.onComplete()
        }.subscribe({
            setLastCacheTime(System.currentTimeMillis())
            isCashed = true
        })
    }


    override fun getPosts(): Single<ArrayList<PostEntity>> {
        Log.e("PostsCashe", "DataFromCashe")
        return Single.just(postsSingle)
    }


    override fun isCached(): Boolean {
        return isCashed
    }

    override fun setLastCacheTime(lastCache: Long) {
        mLastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        return !(isCashed && (System.currentTimeMillis() - mLastCacheTime) < CasheUtil.CASHE_TIME)
    }
}