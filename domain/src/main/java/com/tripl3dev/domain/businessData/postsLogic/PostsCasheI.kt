package com.tripl3dev.domain.repository.businessData.postsLogic

import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.repository.base.baseDataRetrieveMechanism.BaseCasheI
import io.reactivex.Single

interface PostsCasheI :PostsBaseStore,BaseCasheI{
    fun  savePosts(posts : ArrayList<PostEntity>)
}