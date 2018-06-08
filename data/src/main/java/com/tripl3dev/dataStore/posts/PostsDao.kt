package com.tripl3dev.dataStore.posts

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tripl3dev.domain.Entity.PostEntity
import io.reactivex.Single

@Dao
interface PostsDao {

    @Query("SELECT * from Posts_table")
    fun getPosts(): Single<List<PostEntity>>

    @Query("DELETE  from posts_table")
    fun clearCachedPosts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePosts(postsList: ArrayList<PostEntity>)
}