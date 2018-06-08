package com.tripl3dev.dataStore.posts

import android.util.Log
import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.businessLogic.dataLogic.postsLogic.PostsCasheI
import com.tripl3dev.domain.managers.CasheUtil
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsCasheImp @Inject constructor(val dataBase: PostsDao) : PostsCasheI {

    private var isCashed: Boolean = false
    private var mLastCacheTime: Long = 0



    override fun savePosts(posts: ArrayList<PostEntity>) {
        Completable.create {
            dataBase.clearCachedPosts()
            dataBase.savePosts(posts)
            it.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
            setLastCacheTime(System.currentTimeMillis())
            isCashed = true
        })
    }


    override fun getPosts(): Single<ArrayList<PostEntity>> {
        Log.e("PostsCashe", "DataFromCashe")
        return dataBase.getPosts().map { ArrayList(it) }

    }


    override fun isCached(): Boolean {
        return isCashed
    }

    override fun setLastCacheTime(lastCache: Long) {
        mLastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        return (isCashed && (System.currentTimeMillis() - mLastCacheTime) > CasheUtil.CASHE_TIME)
    }
}