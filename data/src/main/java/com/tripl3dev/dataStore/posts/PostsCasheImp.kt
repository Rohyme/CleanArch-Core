package com.tripl3dev.dataStore.posts

import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.managers.CasheUtil
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsCasheI
import io.reactivex.*


class PostsCasheImp : PostsCasheI {

    var postsSingle: ArrayList<PostEntity> = ArrayList()
    var isCashed: Boolean = false
        get() = postsSingle.isNotEmpty()
    private var mLastCacheTime: Long = 0


    override fun savePosts(posts: ArrayList<PostEntity>) {
        Completable.create {
            postsSingle.addAll(posts)
            it.onComplete()
        }.subscribe({
            setLastCacheTime(System.currentTimeMillis())
            isCashed = true
        })
    }


    override fun getPosts(): Single<ArrayList<PostEntity>> {
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